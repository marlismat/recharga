package ve.com.digitel.rechargeorchestratorbdp.proxy;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
import ve.com.digitel.canonicalbusinessinteractionobjects.to.FunctionalMessage;
import ve.com.digitel.canonicalbusinessinteractionobjects.to.IPAddress;
import ve.com.digitel.canonicalbusinessinteractionobjects.to.Party;
import ve.com.digitel.canonicalbusinessinteractionobjects.to.PartyInteractionRole;
import ve.com.digitel.canonicalbusinessinteractionobjects.to.PartyRole;
import ve.com.digitel.canonicalbusinessinteractionobjects.to.Payment;
import ve.com.digitel.rechargeorchestratorbdp.bean.OperationBean;
import ve.com.digitel.rechargeorchestratorbdp.exception.ProxyRechargeValidationSolicitudeException;
import ve.com.digitel.rechargeorchestratorbdp.util.AppProperties;
import ve.com.digitel.rechargeorchestratorbdp.util.CalendarConverter;
import ve.com.digitel.rechargevalidationsolicitude.to.request.RechargeValidationSolicitudeRequest;
import ve.com.digitel.rechargevalidationsolicitude.to.response.RechargeValidationSolicitudeResponse;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.BigDecimalConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class RechargeValidationSolicitudeHttpClient {
	
	private static Logger logger= BSSIntLogger.getBSSIntLogger(RechargeValidationSolicitudeHttpClient.class);
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

	public String getContentEncoding() {
		return contentEncoding;
	}

	public void setContentEncoding(String contentEncoding) {
		this.contentEncoding = AppProperties.getProperty(contentEncoding);
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
		RechargeValidationSolicitudeHttpClient.httpClient = httpClient;
	}
	
	/**
	 * 
	 */
	public void init () {
		logger.info("Cargado el delegador: ".concat(this.getClass().getSimpleName()));
	}	
	

   public void makeOperation(OperationBean operation,String interactionDate,String orderNumber,Map<String,String> parameters) throws Exception{
	 
	    String resultContents = null;
		String contents       = null;
		PostMethod method =null;
		HttpClient httpClient = null;
		int statusCode=0;
		boolean isError=false;
						
		try{
				httpClient = new HttpClient();
				httpClient.getParams().setParameter("http.connection.timeout", new Integer(getTimeOutHttp()));
				method=new PostMethod(getEndPointURL());

				System.out.println("RechargeValidationSolicitudeHttpClient - MakeOperation - Line 149");
				System.out.println(getEndPointURL());
				method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
				
				String xmlString =parseRequest(createRequest(operation,interactionDate,orderNumber,parameters));
				StringRequestEntity str = new StringRequestEntity(xmlString
						,getContentType()
						,getContentEncoding());
				method.setRequestEntity(str);
				if(logger.isDebugEnabled()){
					logger.debug("Request: "+xmlString);
					
				}
				statusCode = httpClient.executeMethod(method);
								
				if(!String.valueOf(statusCode).equalsIgnoreCase(getHttpRespOk().trim())){
						isError=true;
						logger.error("Ocurrio un error en la invocación al servicio RechargeValidationSolicitude ");
						logger.error("Codigo que devuelve el metodo POST: "+statusCode);
				}
				if(!isError){				
					contents= method.getResponseBodyAsString();
					resultContents= contents.toString();
					if(logger.isDebugEnabled()){
						logger.debug("Response: "+resultContents);
						
					}
					RechargeValidationSolicitudeResponse response=parseResponse(resultContents);
					
					if(response.getCustomerOrder().getFunctionalMessage()!=null){
						
						if(response.getCustomerOrder().getFunctionalMessage().getCode().equalsIgnoreCase(getRespOk())){
							logger.info("Cumple con las reglas de negocio para realizar aplicar recarga dentro de la plataforma de Digitel...");
							operation.setPurchaseOrderNumber(response.getCustomerOrder().getPurchaseOrderNumber());
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
				logger.error("Error ocurrido durante la invocacion al proxy del servicio RechargeValidationSolicitude", e);
				resultContents=null;
				isError=true;
			}finally{				
				httpClient=null;
				method.releaseConnection();
				if(isError){
					throw new ProxyRechargeValidationSolicitudeException();
				}
			}
			
	             
	    
   }

   private RechargeValidationSolicitudeResponse parseResponse(String xml)throws Exception {
	   XStream xstream=new XStream(new DomDriver());
	   prepareXstream(xstream);
	   xml=xml.replaceAll("<[?]xml version='1.0' encoding='UTF-8'[?]>","").
			   replaceAll("<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">","").
			   replaceAll("<S:Body>","").
			   replaceAll("<ns2:executeResponse xmlns:ns2=\"http://services.rechargevalidationsolicitude.digitel.com.ve/\">","").
			   replaceAll("</ns2:executeResponse>","").
			   replaceAll("</S:Body>","").
			   replaceAll("</S:Envelope>","");
	   RechargeValidationSolicitudeResponse response=(RechargeValidationSolicitudeResponse) xstream.fromXML(xml);
	  
	   return  response;
   }
   
   private void prepareXstream(XStream xstream){
	   xstream.alias("customerOrderItem",CustomerOrderItem.class);
	   xstream.alias("RechargeValidationSolicitudeRequest",RechargeValidationSolicitudeRequest.class);
	   xstream.alias("RechargeValidationSolicitudeResponse",RechargeValidationSolicitudeResponse.class);
	   xstream.alias("functionalMessage",FunctionalMessage.class);
	   xstream.alias("customerAccountInteractionRole",CustomerAccountInteractionRole.class);
	   xstream.alias("payment",Payment.class);
	   xstream.alias("payment",Payment.class);
	   xstream.alias("partyRole", PartyRole.class);
	   xstream.alias("iPAddress", IPAddress.class);
	   xstream.addImplicitCollection(CustomerOrder.class,"customerOrderItem","customerOrderItem",CustomerOrderItem.class);
	   xstream.addImplicitCollection(CustomerOrderItem.class,"customerAccountInteractionRole","customerAccountInteractionRole",CustomerAccountInteractionRole.class);
	   xstream.addImplicitCollection(CustomerAccount.class,"payment","payment",Payment.class);
	   xstream.addImplicitCollection(PartyInteractionRole.class,"partyRole","partyRole",PartyRole.class);
	   xstream.addImplicitCollection(PartyRole.class,"iPAddress","iPAddress",IPAddress.class);
	   xstream.registerConverter(new CalendarConverter());
	   xstream.registerLocalConverter(CustomerOrderItem.class,"orderItemNumber",new BigDecimalConverter());
   }

   private String parseRequest(RechargeValidationSolicitudeRequest request)throws Exception {
	   String requestXml="";
	   XStream xstream=new XStream();
	   prepareXstream(xstream);
	   requestXml=xstream.toXML(request);
	   
	   requestXml="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.rechargevalidationsolicitude.digitel.com.ve/\">"+
			   "<soapenv:Header/>"+
			   "<soapenv:Body>"+
			   	"<ser:execute>"+
			   		requestXml+
			   	"</ser:execute>"+
			   "</soapenv:Body>"+
			  "</soapenv:Envelope>";
      
	   return requestXml;
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

   private RechargeValidationSolicitudeRequest createRequest(OperationBean operation,String interactionDate,String orderNumber,Map<String,String> parameters) throws Exception {
	   
	   RechargeValidationSolicitudeRequest request=new RechargeValidationSolicitudeRequest();
	   Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(AppProperties.getProperty("TIME_ZONE")));
	   Calendar cal2 = Calendar.getInstance(TimeZone.getTimeZone(AppProperties.getProperty("TIME_ZONE")));
	   request.setCustomerOrder(new CustomerOrder());
	   request.getCustomerOrder().setId(operation.getId());
	   request.getCustomerOrder().setOrderItemNumber(new BigDecimal(orderNumber));
	   request.getCustomerOrder().setCustomerOrderType(AppProperties.getProperty("RECHARGE_VALIDATION_CUSTOMER_ORDER_TYPE"));
	   request.getCustomerOrder().setPurchaseOrderNumber("0"); 
	   cal.setTime(stringToDate(interactionDate));
	   request.getCustomerOrder().setInteractionDate(cal);
	   request.getCustomerOrder().setPartyInteractionRole(new PartyInteractionRole());
	   request.getCustomerOrder().getPartyInteractionRole().setInteractionRole(AppProperties.getProperty("RECHARGE_VALIDATION_INTERACTION_ROLE")); 
	   request.getCustomerOrder().getPartyInteractionRole().setPartyRole(new ArrayList<PartyRole>());
	   PartyRole ipAddress=new PartyRole(); ipAddress.setIPAddress(new ArrayList<IPAddress>());
	   ipAddress.setName(AppProperties.getProperty("RECHARGE_VALIDATION_PARAM_IPADDRESS"));
	   IPAddress ip=new IPAddress(); ip.setNetworkNumber(operation.getIp());  ipAddress.getIPAddress().add(ip);
	   request.getCustomerOrder().getPartyInteractionRole().getPartyRole().add(ipAddress);
	   
	  
	   
	   String channels=parameters.get(AppProperties.getProperty("RECHARGE_VALIDATION_PARAM_CHANNEL"));
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
	   
	   
	   PartyRole channel_ptf=new PartyRole(); channel_ptf.setName(AppProperties.getProperty("RECHARGE_VALIDATION_PARAM_CHANNEL")+"_PTF");
	   channel_ptf.setParty(new Party()); channel_ptf.getParty().setPartyId(Integer.parseInt(idChannelPtf));
	   
	   PartyRole channel=new PartyRole();
	   channel.setName(AppProperties.getProperty("RECHARGE_VALIDATION_PARAM_CHANNEL")); channel.setParty(new Party());
	   channel.getParty().setName(nameChannel);  channel.getParty().setPartyId(Integer.parseInt(idChannel));		   
	   
	   request.getCustomerOrder().getPartyInteractionRole().getPartyRole().add(channel_ptf);
	   request.getCustomerOrder().getPartyInteractionRole().getPartyRole().add(channel);
	   
	   
	   PartyRole institucion=new PartyRole();
	   institucion.setName(AppProperties.getProperty("RECHARGE_VALIDATION_PARAM_INSTITUTION")+"_PTF");  institucion.setParty(new Party());
	   String institucionV=parameters.get(AppProperties.getProperty("RECHARGE_VALIDATION_PARAM_INSTITUTION"));
	   institucionV=institucionV.substring(0,institucionV.indexOf(AppProperties.getProperty("SEPARATOR"))).trim();
	   institucion.getParty().setPartyId(Integer.parseInt(institucionV));
	   request.getCustomerOrder().getPartyInteractionRole().getPartyRole().add(institucion);
	   
	   PartyRole comercio=new PartyRole();
	   comercio.setName(AppProperties.getProperty("RECHARGE_VALIDATION_PARAM_COMMERCE")+"_PTF"); comercio.setParty(new Party());
	   String comercioV=parameters.get(AppProperties.getProperty("RECHARGE_VALIDATION_PARAM_COMMERCE"));
	   comercioV=comercioV.substring(0,comercioV.indexOf(AppProperties.getProperty("SEPARATOR"))).trim();
	   comercio.getParty().setPartyId(Integer.parseInt(comercioV));
	   request.getCustomerOrder().getPartyInteractionRole().getPartyRole().add(comercio);
	   
	   PartyRole terminal=new PartyRole(); terminal.setName(AppProperties.getProperty("RECHARGE_VALIDATION_PARAM_TERMINAL")+"_PTF"); terminal.setParty(new Party());
	   String terminalV=parameters.get(AppProperties.getProperty("RECHARGE_VALIDATION_PARAM_TERMINAL"));
	   terminalV=terminalV.substring(0,terminalV.indexOf(AppProperties.getProperty("SEPARATOR"))).trim();
	   terminal.getParty().setPartyId(Integer.parseInt(terminalV));
	   request.getCustomerOrder().getPartyInteractionRole().getPartyRole().add(terminal);
	   
	      
	   request.getCustomerOrder().setCustomerOrderItem(new ArrayList<CustomerOrderItem>());
	   CustomerOrderItem customerOrderItem=new CustomerOrderItem();
	   customerOrderItem.setId(operation.getId());
	   customerOrderItem.setCustomerAccountInteractionRole(new ArrayList<CustomerAccountInteractionRole>());
	   CustomerAccountInteractionRole beneficiary=new CustomerAccountInteractionRole();
	   CustomerAccountInteractionRole affiliate=new CustomerAccountInteractionRole();
	   
	   
	   /*************NEW 26-11-2018******************/
	   /*Payment payment=new Payment();
	   payment.setId(operation.getBankResponse().getId());
	   payment.setConfirmationNumber(Long.parseLong(operation.getBankResponse().getReference()));
	   payment.setAmount(Double.parseDouble(operation.getAmount()));
	   
	   
	   
	   //Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(AppProperties.getProperty("TIME_ZONE")));
	   cal.setTime(stringToDate(interactionDate));
	   payment.setPaymentDate(cal);
	   payment.setCardPM(new CardPM());
	   payment.getCardPM().setId(AppProperties.getProperty("RECHARGESOLICITUDE_CARDPMID"));
	   payment.getCardPM().setPaymentMethodType(operation.getFormOfPay());
	   payment.getCardPM().setIdCardHolderName(operation.getCreditCardHolderId());
	   payment.getCardPM().setCardNumber(enmascarar(operation.getCreditCardNumber()));
	   //beneficiary.getCustomerAccount().getPayment().add(payment);
	   //customerOrderItem.getCustomerAccountInteractionRole().add(beneficiary);
	   /***************NEW 26-11-2018********************/
	   
	   beneficiary.setInteractionRole(AppProperties.getProperty("RECHARGE_VALIDATION_PARAM_BENEFICIARY"));
	   beneficiary.setCustomerAccount(new CustomerAccount());
	   beneficiary.getCustomerAccount().setId(operation.getBeneficiary());
	   beneficiary.getCustomerAccount().setPayment(new ArrayList<Payment>());
	   Payment pay=new Payment(); 
	   pay.setId(operation.getId()); 
	   pay.setAmount(Double.parseDouble(operation.getAmount()));
	   cal2.setTime(stringToDate(operation.getOperationDateTime())); 
	   pay.setPaymentDate(cal2);
	  
	  
	   beneficiary.getCustomerAccount().getPayment().add(pay);
	   
	   
	   affiliate.setInteractionRole(AppProperties.getProperty("RECHARGE_VALIDATION_PARAM_AFFILIATE")); 
	   affiliate.setCustomerAccount(new CustomerAccount()); 
	   affiliate.getCustomerAccount().setId(operation.getAffiliate());
	   affiliate. getCustomerAccount().setCardPM(new ArrayList<CardPM>());
	   Payment pay2=new Payment();
	   
	   CardPM card = new CardPM();
	   card.setId(AppProperties.getProperty("RECHARGESOLICITUDE_CARDPMID"));
	   card.setPaymentMethodType(operation.getFormOfPay());
	   card.setIdCardHolderName(operation.getCreditCardHolderId());
	   System.out.println(operation.getCreditCardNumber());
	   card.setCardNumber(enmascarar(operation.getCreditCardNumber()));
	   
	   
	   List<CardPM> aa = new ArrayList<CardPM>();

	   aa.add(card);
	   
	   affiliate.getCustomerAccount().setCardPM(aa);
	   
	   	   
	   customerOrderItem.getCustomerAccountInteractionRole().add(beneficiary);
	   customerOrderItem.getCustomerAccountInteractionRole().add(affiliate);
	   request.getCustomerOrder().getCustomerOrderItem().add(customerOrderItem);
	     System.out.println(request);  
	   return request;
   }
   
   private String enmascarar(String cadena) throws Exception {
	    short nDigit=Short.parseShort(AppProperties.getProperty("RECHARGESOLICITUDE_NDIGITB"));
	    char[] chars = new char[nDigit];
	    Arrays.fill(chars, AppProperties.getProperty("RECHARGESOLICITUDE_DIGITM").charAt(0));
	    cadena=cadena.substring(0,cadena.length()-nDigit);
		return cadena.concat(new String(chars));
	}
}