package ve.com.digitel.rechargeorchestratorbdp.proxy;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

import ve.com.digitel.bssint.log.BSSIntLogger;
import ve.com.digitel.canonicalbusinessinteractionobjects.to.CardPM;
import ve.com.digitel.canonicalbusinessinteractionobjects.to.CustomerAccount;
import ve.com.digitel.canonicalbusinessinteractionobjects.to.CustomerAccountInteractionRole;
import ve.com.digitel.canonicalbusinessinteractionobjects.to.CustomerOrder;
import ve.com.digitel.canonicalbusinessinteractionobjects.to.CustomerOrderItem;
import ve.com.digitel.canonicalbusinessinteractionobjects.to.EmailContact;
import ve.com.digitel.canonicalbusinessinteractionobjects.to.Party;
import ve.com.digitel.canonicalbusinessinteractionobjects.to.PartyInteractionRole;
import ve.com.digitel.canonicalbusinessinteractionobjects.to.PartyRole;
import ve.com.digitel.canonicalbusinessinteractionobjects.to.Payment;
import ve.com.digitel.rechargeorchestratorbdp.bean.OperationBean;
import ve.com.digitel.rechargeorchestratorbdp.exception.ProxyRechargeSolicitudeException;
import ve.com.digitel.rechargeorchestratorbdp.util.AppProperties;
import ve.com.digitel.rechargeorchestratorbdp.util.CalendarConverter;
import ve.com.digitel.rechargesolicitude.to.request.RechargeSolicitudeRequest;
import ve.com.digitel.rechargesolicitude.to.response.RechargeSolicitudeResponse;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class RechargeSolicitudeHttpClient {
	
	private static Logger logger= BSSIntLogger.getBSSIntLogger(RechargeSolicitudeHttpClient.class);
	private static HttpClient   httpClient= null;
	private String endPointURL;		
	private String timeOutHttp;
	private String respOk;
	private String httpRespOk;
	private String contentType;
	private String contentEncoding;
	
		
			
	/**
	 * @return the timeOutHttp
	 */
	public String getTimeOutHttp() {
		return timeOutHttp;
	}

	/**
	 * @param timeOutHttp the timeOutHttp to set
	 */
	public void setTimeOutHttp(String timeOutHttp) {
		this.timeOutHttp = AppProperties.getProperty(timeOutHttp);
	}
	
	public String getContentEncoding() {
		return contentEncoding;
	}

	public void setContentEncoding(String contentEncoding) {
		this.contentEncoding = AppProperties.getProperty(contentEncoding);
	}
	
	public String getRespOk() {
		return respOk;
	}

	public void setRespOk(String respOk) {
		this.respOk = AppProperties.getProperty(respOk);
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = AppProperties.getProperty(contentType);
	}

	public String getHttpRespOk() {
		return httpRespOk;
	}

	public void setHttpRespOk(String httpRespOk) {
		this.httpRespOk = AppProperties.getProperty(httpRespOk);
	}

	/**
	 * @return the endPointURL
	 */
	public String getEndPointURL(){
		return endPointURL;
	}

	/**
	 * @param endPointURL to set
	 */
	public void setEndPointURL(String endPointURL) {
		this.endPointURL = AppProperties.getProperty(endPointURL);
	}

	/**
	 * @return the httpClient
	 */
	public static HttpClient getHttpClient() {
		return httpClient;
	}

	/**
	 * @param httpClient the httpClient to set
	 */
	public static void setHttpClient(HttpClient httpClient) {
		RechargeSolicitudeHttpClient.httpClient = httpClient;
	}
	
	/**
	 * 
	 */
	public void init () {
		logger.info("Cargado el delegador: ".concat(this.getClass().getSimpleName()));
	}	
	

   public void makeOperation(OperationBean operation,String interactionDate,String orderNumber,String id,Map<String,String> parameters) throws Exception{
	 
	 
	   	String resultContents = null;
		String contents       = null;
		PostMethod method =null;
		HttpClient httpClient = null;
		int statusCode=0;
		boolean isError=false;
						
		try{
				httpClient = new HttpClient();
				httpClient.getParams().setParameter("http.connection.timeout", new Integer(getTimeOutHttp()));
				System.out.println("1");
				method=new PostMethod(getEndPointURL());
				method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
				System.out.println("2");
				String xmlString =parseRequest(createRequest(operation,interactionDate,orderNumber,parameters));
				System.out.println("3");
				StringRequestEntity str = new StringRequestEntity(xmlString
						,getContentType()
						,getContentEncoding());
				method.setRequestEntity(str);
				if(logger.isDebugEnabled()){ 
					logger.debug("ENTROOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
					logger.debug("Request: "+xmlString);
					logger.debug("ENTROOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
					
				}
				statusCode = httpClient.executeMethod(method);
				
				if(!String.valueOf(statusCode).equalsIgnoreCase(getHttpRespOk().trim())){
					isError=true;
					logger.error("Ocurrio un error en la invocación al servicio RechargeSolicitude ");
					logger.error("Codigo que devuelve el metodo POST: "+statusCode);
				}
				if(!isError){				
					contents= method.getResponseBodyAsString();
					resultContents= contents.toString();
					if(logger.isDebugEnabled()){
						logger.debug("Response: "+resultContents);
						
					}
					RechargeSolicitudeResponse response=parseResponse(resultContents);
					if(response.getCustomerOrder().getFunctionalMessage()!=null){
					
						if(response.getCustomerOrder().getFunctionalMessage().getCode().equalsIgnoreCase(getRespOk())){
							logger.info("Se registro Exitosamente la recarga en la plataforma de Digitel...");		
						}else{
							isError=true;
							operation.setCode(response.getCustomerOrder().getFunctionalMessage().getCode());
							operation.setMessage(response.getCustomerOrder().getFunctionalMessage().getMessage());
							logger.error("Ocurrio un error proveniente del servicio:");
							logger.error("Código: "+response.getCustomerOrder().getFunctionalMessage().getCode());
							logger.error("Mensaje: "+response.getCustomerOrder().getFunctionalMessage().getMessage());
							logger.error("Código Causa: "+response.getCustomerOrder().getFunctionalMessage().getCauseCode());
							logger.error("Mensaje Causa:"+response.getCustomerOrder().getFunctionalMessage().getCauseMessage());
						}
						
						
					}else{
						isError=true;
						operation.setCode(response.getCustomerOrder().getErrorMessage().getCode());
						operation.setMessage(response.getCustomerOrder().getErrorMessage().getMessage());
						logger.error("Ocurrio un error proveniente del servicio:");
						logger.error("Código: "+response.getCustomerOrder().getErrorMessage().getCode());
						logger.error("Mensaje: "+response.getCustomerOrder().getErrorMessage().getMessage());
						logger.error("Traza: "+response.getCustomerOrder().getErrorMessage().getTrace());					
					}
				}	
			}catch(Exception e){
				logger.error("Error ocurrido durante la invocación al servicio RechargeSolicitude", e);
				resultContents=null;
				isError=true;
			}finally{				
				httpClient=null;
				method.releaseConnection();
				if(isError){
					throw new ProxyRechargeSolicitudeException();
				}
			}
   }
   
   private void prepareXstream(XStream xstream) throws Exception{
	   xstream.alias("customerOrderItem",CustomerOrderItem.class);
	   xstream.alias("RechargeSolicitudeRequest",RechargeSolicitudeRequest.class);
	   xstream.alias("RechargeSolicitudeResponse",RechargeSolicitudeResponse.class);
	   xstream.alias("customerAccountInteractionRole",CustomerAccountInteractionRole.class);
	   xstream.alias("payment",Payment.class);
	   xstream.alias("partyRole", PartyRole.class);
	   xstream.alias("emailContact", EmailContact.class);
	   xstream.addImplicitCollection(CustomerOrder.class,"customerOrderItem","customerOrderItem",CustomerOrderItem.class);
	   xstream.addImplicitCollection(CustomerOrderItem.class,"customerAccountInteractionRole","customerAccountInteractionRole",CustomerAccountInteractionRole.class);
	   xstream.addImplicitCollection(CustomerAccount.class,"payment","payment",Payment.class);
	   xstream.addImplicitCollection(PartyInteractionRole.class,"partyRole","partyRole",PartyRole.class);
	   xstream.addImplicitCollection(PartyRole.class,"emailContact","emailContact",EmailContact.class);
	   xstream.registerConverter(new CalendarConverter());
   }
   

   private RechargeSolicitudeResponse parseResponse(String xml) throws Exception {
	   
	   XStream xstream=new XStream(new DomDriver());
	   prepareXstream(xstream);
	   xml=xml.replaceAll("<[?]xml version='1.0' encoding='UTF-8'[?]>","").
	   replaceAll("<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">","").
	   replaceAll("<S:Body>","").
	   replaceAll("<ns2:executeResponse xmlns:ns2=\"http://services.rechargesolicitude.digitel.com.ve/\">","").
	   replaceAll("</ns2:executeResponse>","").
	   replaceAll("</S:Body>","").
	   replaceAll("</S:Envelope>","");
	   RechargeSolicitudeResponse response=(RechargeSolicitudeResponse) xstream.fromXML(xml);
	   return response;
   }

   private String parseRequest(RechargeSolicitudeRequest request) throws Exception {
	   String requestXML=null;
	   XStream xstream=new XStream();
	   prepareXstream(xstream);
	   requestXML=xstream.toXML(request);
	   String id = "<customerOrderItem> <id>010_04120000272_20190109093000</id>";
//	   C+request.getCustomerOrder().getId().toString();
	   requestXML=requestXML.replace("<customerOrderItem>", id);
	   System.out.println(requestXML);
	   requestXML="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.rechargesolicitude.digitel.com.ve/\">"+
	   "<soapenv:Header/>"+
	   		"<soapenv:Body>"+
	   			"<ser:execute>"+
	   				requestXML+
	   			"</ser:execute>"+
	   		"</soapenv:Body>"+
	   "</soapenv:Envelope>";
	   return requestXML;
   }

   private  RechargeSolicitudeRequest createRequest(OperationBean operation, String interactionDate,String orderNumber,
		   Map<String, String> parameters) throws Exception {
	   RechargeSolicitudeRequest request=new RechargeSolicitudeRequest();
	   request.setCustomerOrder(new CustomerOrder());
	   request.getCustomerOrder().setId(operation.getPurchaseOrderNumber());
	   request.getCustomerOrder().setOrderItemNumber(new BigDecimal(0));
	   System.out.println("3");
	   request.getCustomerOrder().setCustomerOrderType(AppProperties.getProperty("RECHARGESOLICITUDE_CUSTOMER_ORDER_TYPE"));
	   request.getCustomerOrder().setPurchaseOrderNumber(operation.getPurchaseOrderNumber());
	   request.getCustomerOrder().setCustomerOrderItem(new ArrayList<CustomerOrderItem>());
	   CustomerOrderItem item=new CustomerOrderItem();
	   item.setId(operation.getPurchaseOrderNumber());
	   item.setCustomerAccountInteractionRole(new ArrayList<CustomerAccountInteractionRole>());
	   CustomerAccountInteractionRole affiliate=new CustomerAccountInteractionRole();
	   CustomerAccountInteractionRole beneficiary=new CustomerAccountInteractionRole();
	   affiliate.setInteractionRole(AppProperties.getProperty("RECHARGESOLICITUDE_PARAM_AFFILIATE"));
	   affiliate.setCustomerAccount(new CustomerAccount());
	   affiliate.getCustomerAccount().setId(operation.getAffiliate());
	   item.getCustomerAccountInteractionRole().add(affiliate);
	   beneficiary.setInteractionRole(AppProperties.getProperty("RECHARGESOLICITUDE_PARAM_BENEFICIARY"));
	   beneficiary.setCustomerAccount(new CustomerAccount());
	   beneficiary.getCustomerAccount().setId(operation.getBeneficiary());
	   beneficiary.getCustomerAccount().setPayment(new ArrayList<Payment>());
	   
	   System.out.println("4");
	   Payment payment=new Payment();
	  
	   /////////// SOLO PRUEBA
	   payment.setId("123456");
	   payment.setConfirmationNumber(1L);
	   
	   ///////////////////////////////////
	   
	   //payment.setId(operation.getBankResponse().getId());
	   //payment.setConfirmationNumber(Long.parseLong(operation.getBankResponse().getReference()));
	   payment.setAmount(Double.parseDouble(operation.getAmount()));
	   
	   System.out.println("5");
	   
	   Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(AppProperties.getProperty("TIME_ZONE")));
	   cal.setTime(stringToDate(interactionDate));
	   payment.setPaymentDate(cal);
	   payment.setCardPM(new CardPM());
	   payment.getCardPM().setId(AppProperties.getProperty("RECHARGESOLICITUDE_CARDPMID"));
	   payment.getCardPM().setPaymentMethodType(operation.getFormOfPay());
	   payment.getCardPM().setIdCardHolderName(operation.getCreditCardHolderId());
	   payment.getCardPM().setCardNumber(enmascarar(operation.getCreditCardNumber()));
	   beneficiary.getCustomerAccount().getPayment().add(payment);
	   item.getCustomerAccountInteractionRole().add(beneficiary);
	   
	   System.out.println("6");
	   
	   item.setPartyInteractionRole(new PartyInteractionRole());
	   item.getPartyInteractionRole().setInteractionRole(AppProperties.getProperty("RECHARGESOLICITUDE_INTERACTIONROLE"));
	   item.getPartyInteractionRole().setPartyRole(new ArrayList<PartyRole>());
	   PartyRole email=new PartyRole();
	   email.setName(AppProperties.getProperty("RECHARGESOLICITUDE_PARAM_EMAIL"));
	   email.setEmailContact(new ArrayList<EmailContact>());
	   EmailContact emailContact=new EmailContact();
	   emailContact.setEMailAddress("");
	   email.getEmailContact().add(emailContact);
	   item.getPartyInteractionRole().getPartyRole().add(email);
	   request.getCustomerOrder().getCustomerOrderItem().add(item);
	   System.out.println("73");
	   
	   request.getCustomerOrder().setPartyInteractionRole(new PartyInteractionRole());
	   request.getCustomerOrder().getPartyInteractionRole().setPartyRole(new ArrayList<PartyRole>());
	   System.out.println("8");
	   String channels=parameters.get(AppProperties.getProperty("RECHARGESOLICITUDE_PARAM_CHANNEL"));
	   String firstLevel[]=channels.split(AppProperties.getProperty("SEPARATOR"));	   
	   String idChannelPtf;
	   String idChannel;
	   String nameChannel=firstLevel[1];
	   String secondLevel[]=firstLevel[0].split(AppProperties.getProperty("SEPARATOR_CHANNEL"));
	   if(secondLevel.length==1)
		   idChannelPtf=AppProperties.getProperty("CHANNEL_PTF_DEFAULT");
	   else
		   idChannelPtf=secondLevel[1];
	   idChannel=secondLevel[0];
	   
	   System.out.println("8");
	   PartyRole channel_ptf=new PartyRole(); channel_ptf.setName(AppProperties.getProperty("RECHARGESOLICITUDE_PARAM_CHANNEL")+"_PTF");
	   channel_ptf.setParty(new Party()); channel_ptf.getParty().setPartyId(Integer.parseInt(idChannelPtf));
	   System.out.println("10");
	   PartyRole channel=new PartyRole();
	   channel.setName(AppProperties.getProperty("RECHARGESOLICITUDE_PARAM_CHANNEL")); channel.setParty(new Party());
	   channel.getParty().setName(nameChannel);  channel.getParty().setPartyId(Integer.parseInt(idChannel));		   
	   System.out.println("11");
	   request.getCustomerOrder().getPartyInteractionRole().getPartyRole().add(channel_ptf);
	   request.getCustomerOrder().getPartyInteractionRole().getPartyRole().add(channel);
	   
	   
	   PartyRole institucion=new PartyRole();
	   institucion.setName(AppProperties.getProperty("RECHARGESOLICITUDE_PARAM_INSTITUTION")+"_PTF");  institucion.setParty(new Party());
	   String institucionV=parameters.get(AppProperties.getProperty("RECHARGESOLICITUDE_PARAM_INSTITUTION"));
	   institucionV=institucionV.substring(0,institucionV.indexOf(AppProperties.getProperty("SEPARATOR"))).trim();
	   institucion.getParty().setPartyId(Integer.parseInt(institucionV));
	   request.getCustomerOrder().getPartyInteractionRole().getPartyRole().add(institucion);
	   
	   PartyRole comercio=new PartyRole();
	   comercio.setName(AppProperties.getProperty("RECHARGESOLICITUDE_PARAM_COMMERCE")+"_PTF"); comercio.setParty(new Party());
	   String comercioV=parameters.get(AppProperties.getProperty("RECHARGESOLICITUDE_PARAM_COMMERCE"));
	   comercioV=comercioV.substring(0,comercioV.indexOf(AppProperties.getProperty("SEPARATOR"))).trim();
	   comercio.getParty().setPartyId(Integer.parseInt(comercioV));
	   request.getCustomerOrder().getPartyInteractionRole().getPartyRole().add(comercio);
	   
	   PartyRole terminal=new PartyRole(); terminal.setName(AppProperties.getProperty("RECHARGESOLICITUDE_PARAM_TERMINAL")+"_PTF"); terminal.setParty(new Party());
	   String terminalV=parameters.get(AppProperties.getProperty("RECHARGESOLICITUDE_PARAM_TERMINAL"));
	   terminalV=terminalV.substring(0,terminalV.indexOf(AppProperties.getProperty("SEPARATOR"))).trim();
	   terminal.getParty().setPartyId(Integer.parseInt(terminalV));
	   request.getCustomerOrder().getPartyInteractionRole().getPartyRole().add(terminal);
	   
	   return request;
   }
   
   private String enmascarar(String cadena) throws Exception {
	    short nDigit=Short.parseShort(AppProperties.getProperty("RECHARGESOLICITUDE_NDIGITB"));
	    char[] chars = new char[nDigit];
	    Arrays.fill(chars, AppProperties.getProperty("RECHARGESOLICITUDE_DIGITM").charAt(0));
	    cadena=cadena.substring(0,cadena.length()-nDigit);
		return cadena.concat(new String(chars));
	}
   
   private Date stringToDate(String date) throws Exception {
	   SimpleDateFormat simpleDateFormat = new SimpleDateFormat(AppProperties.getProperty("FORMAT_DATE"));
	   SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(AppProperties.getProperty("FORMAT_DATE2"));
	   Date fecha;
	   try{
		   fecha=simpleDateFormat.parse(date);
		   return fecha; 		   
		   
	   }catch(Exception e){
		   fecha=simpleDateFormat2.parse(date);
		   return fecha;
	   }
	   
   }
     	 
}