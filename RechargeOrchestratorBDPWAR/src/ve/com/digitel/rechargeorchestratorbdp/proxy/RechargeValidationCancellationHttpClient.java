package ve.com.digitel.rechargeorchestratorbdp.proxy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

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
import ve.com.digitel.canonicalbusinessinteractionobjects.to.Party;
import ve.com.digitel.canonicalbusinessinteractionobjects.to.PartyInteractionRole;
import ve.com.digitel.canonicalbusinessinteractionobjects.to.PartyRole;
import ve.com.digitel.canonicalbusinessinteractionobjects.to.Payment;
import ve.com.digitel.rechargeorchestratorbdp.bean.OperationBean;
import ve.com.digitel.rechargeorchestratorbdp.exception.ProxyRechargeValidationCancellationException;
import ve.com.digitel.rechargeorchestratorbdp.util.AppProperties;
import ve.com.digitel.rechargeorchestratorbdp.util.CalendarConverter;
import ve.com.digitel.rechargevalidationcancellation.to.request.RechargeValidationCancellationRequest;
import ve.com.digitel.rechargevalidationcancellation.to.response.RechargeValidationCancellationResponse;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


public class RechargeValidationCancellationHttpClient {
	
	private static Logger logger= BSSIntLogger.getBSSIntLogger(RechargeValidationCancellationHttpClient.class);
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

	public String getRespOk() {
		return respOk;
	}

	public void setRespOk(String respOk) {
		this.respOk = AppProperties.getProperty(respOk);
	}

	public String getHttpRespOk() {
		return httpRespOk;
	}

	public void setHttpRespOk(String httpRespOk) {
		this.httpRespOk = AppProperties.getProperty(httpRespOk);
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = AppProperties.getProperty(contentType);
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
		RechargeValidationCancellationHttpClient.httpClient = httpClient;
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
				method=new PostMethod(getEndPointURL());
				method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
				String xmlString =parseRequest(createRequest(operation,interactionDate,orderNumber,id,parameters));
				
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
					logger.error("Ocurrio un error en la invocación al servicio RechargeValidationCancellation ");
					logger.error("Codigo que devuelve el metodo POST: "+statusCode);
				}
				if(!isError){				
					contents= method.getResponseBodyAsString();
					resultContents= contents.toString();
					if(logger.isDebugEnabled()){
						logger.debug("Response: "+resultContents);
						
					}
					RechargeValidationCancellationResponse response=parseResponse(resultContents);
					
					if(response.getCustomerOrder().getFunctionalMessage()!=null){
						
						if(response.getCustomerOrder().getFunctionalMessage().getCode().equalsIgnoreCase(getRespOk())){
							logger.info("se cancelo exitosamente la peticion de recarga dentro de la plataforma de Digitel...");
							
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
				logger.error("Error ocurrido durante la invocación al servicio RechargeValidationCancellation", e);
				resultContents=null;
				isError=true;
			}finally{				
				httpClient=null;
				method.releaseConnection();
				if(isError){
					throw new ProxyRechargeValidationCancellationException();
				}
			}
			
	             
	    
   }

   private String parseRequest(RechargeValidationCancellationRequest request) {
	   String requestXml="";
	   XStream xstream=new XStream();
	   prepareXstream(xstream);
	   requestXml=xstream.toXML(request);
	   requestXml="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.rechargevalidationcancellation.digitel.com.ve/\">"+
			   "<soapenv:Header/>"+
			   "<soapenv:Body>"+
			   	"<ser:execute>"+
			   		requestXml+
			   	"</ser:execute>"+
			   "</soapenv:Body>"+
			  "</soapenv:Envelope>";
	   
	   return requestXml;
   }

   private RechargeValidationCancellationRequest createRequest(OperationBean operation, String interactionDate,
		String orderNumber,String id ,Map<String, String> parameters) throws Exception {
	   RechargeValidationCancellationRequest request=new RechargeValidationCancellationRequest();
	   Calendar cal=Calendar.getInstance();
	   request.setCustomerOrder(new CustomerOrder());
	   request.getCustomerOrder().setId(id);
	   request.getCustomerOrder().setCustomerOrderType(AppProperties.getProperty("RECHARGEVALIDATIONCANCELLATION_CUSTOMER_ORDER_TYPE"));
	   request.getCustomerOrder().setPurchaseOrderNumber(operation.getPurchaseOrderNumber());
	   request.getCustomerOrder().setCustomerOrderItem(new ArrayList<CustomerOrderItem>());
	   CustomerOrderItem item=new CustomerOrderItem();
	   item.setCustomerAccountInteractionRole(new ArrayList<CustomerAccountInteractionRole>());
	   CustomerAccountInteractionRole beneficiary=new CustomerAccountInteractionRole();
	   beneficiary.setInteractionRole(AppProperties.getProperty("RECHARGEVALIDATIONCANCELLATION_PARAM_BENEFICIARY"));
	   beneficiary.setCustomerAccount(new CustomerAccount());
	   beneficiary.getCustomerAccount().setId(operation.getBeneficiary());
	   beneficiary.getCustomerAccount().setPayment(new ArrayList<Payment>());
	   Payment payment=new Payment();
	   payment.setAmount(Double.parseDouble(operation.getAmount()));
	   payment.setConfirmationNumber(Long.parseLong("0"));
	   cal.setTime(stringToDate(operation.getOperationDateTime())); payment.setPaymentDate(cal);
	   payment.setCardPM(new CardPM());
	   payment.getCardPM().setPaymentMethodType(operation.getFormOfPay());
	   payment.getCardPM().setCardNumber(enmascarar(operation.getCreditCardNumber()));
	      
	   beneficiary.getCustomerAccount().getPayment().add(payment);
	   CustomerAccountInteractionRole affiliate=new CustomerAccountInteractionRole();
	   affiliate.setInteractionRole(AppProperties.getProperty("RECHARGEVALIDATIONCANCELLATION_PARAM_AFFILIATE"));
	   affiliate.setCustomerAccount(new CustomerAccount());
	   affiliate.getCustomerAccount().setId(operation.getAffiliate());
	      
	   item.getCustomerAccountInteractionRole().add(beneficiary);
	   item.getCustomerAccountInteractionRole().add(affiliate);
	   request.getCustomerOrder().getCustomerOrderItem().add(item);
	   request.getCustomerOrder().setPartyInteractionRole(new PartyInteractionRole());
	   String channels=parameters.get(AppProperties.getProperty("RECHARGEVALIDATIONCANCELLATION_PARAM_CHANNEL"));
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
	   
	   
	   PartyRole channel_ptf=new PartyRole(); channel_ptf.setName(AppProperties.getProperty("RECHARGEVALIDATIONCANCELLATION_PARAM_CHANNEL")+"_PTF");
	   channel_ptf.setParty(new Party()); channel_ptf.getParty().setPartyId(Integer.parseInt(idChannelPtf));
	   
	   PartyRole channel=new PartyRole();
	   channel.setName(AppProperties.getProperty("RECHARGEVALIDATIONCANCELLATION_PARAM_CHANNEL")); channel.setParty(new Party());
	   channel.getParty().setName(nameChannel);  channel.getParty().setPartyId(Integer.parseInt(idChannel));		   
	   
	   request.getCustomerOrder().getPartyInteractionRole().setPartyRole(new ArrayList<PartyRole>());
	   request.getCustomerOrder().getPartyInteractionRole().getPartyRole().add(channel_ptf);
	   request.getCustomerOrder().getPartyInteractionRole().getPartyRole().add(channel);
	   
	   
	   PartyRole institucion=new PartyRole();
	   institucion.setName(AppProperties.getProperty("RECHARGEVALIDATIONCANCELLATION_PARAM_INSTITUTION")+"_PTF");  institucion.setParty(new Party());
	   String institucionV=parameters.get(AppProperties.getProperty("RECHARGEVALIDATIONCANCELLATION_PARAM_INSTITUTION"));
	   institucionV=institucionV.substring(0,institucionV.indexOf(AppProperties.getProperty("SEPARATOR"))).trim();
	   institucion.getParty().setPartyId(Integer.parseInt(institucionV));
	   request.getCustomerOrder().getPartyInteractionRole().getPartyRole().add(institucion);
	   
	   PartyRole comercio=new PartyRole();
	   comercio.setName(AppProperties.getProperty("RECHARGEVALIDATIONCANCELLATION_PARAM_COMMERCE")+"_PTF"); comercio.setParty(new Party());
	   String comercioV=parameters.get(AppProperties.getProperty("RECHARGEVALIDATIONCANCELLATION_PARAM_COMMERCE"));
	   comercioV=comercioV.substring(0,comercioV.indexOf(AppProperties.getProperty("SEPARATOR"))).trim();
	   comercio.getParty().setPartyId(Integer.parseInt(comercioV));
	   request.getCustomerOrder().getPartyInteractionRole().getPartyRole().add(comercio);
	   
	   PartyRole terminal=new PartyRole(); terminal.setName(AppProperties.getProperty("RECHARGEVALIDATIONCANCELLATION_PARAM_TERMINAL")+"_PTF"); terminal.setParty(new Party());
	   String terminalV=parameters.get(AppProperties.getProperty("RECHARGEVALIDATIONCANCELLATION_PARAM_TERMINAL"));
	   terminalV=terminalV.substring(0,terminalV.indexOf(AppProperties.getProperty("SEPARATOR"))).trim();
	   terminal.getParty().setPartyId(Integer.parseInt(terminalV));
	   request.getCustomerOrder().getPartyInteractionRole().getPartyRole().add(terminal);
	   	   
	   request.getCustomerOrder().setFunctionalMessage(new FunctionalMessage());
	   if(operation.getBankResponse()!=null){
		   request.getCustomerOrder().getFunctionalMessage().setCode(operation.getBankResponse().getCode());
		   request.getCustomerOrder().getFunctionalMessage().setMessage(operation.getBankResponse().getMessage());
	   }else{
	       String messages[]=AppProperties.getMessageProperty("F_MESSAGE_22");
		   request.getCustomerOrder().getFunctionalMessage().setCode(messages[0]);
		   request.getCustomerOrder().getFunctionalMessage().setMessage(messages[1]);
	   }
	
	   return request;
   }

   private RechargeValidationCancellationResponse parseResponse(
		String xml) {
	   XStream xstream=new XStream(new DomDriver());
	   prepareXstream(xstream);
	   xml=xml.replaceAll("<[?]xml version='1.0' encoding='UTF-8'[?]>","").
			   replaceAll("<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">","").
			   replaceAll("<S:Body>","").
			   replaceAll("<ns2:executeResponse xmlns:ns2=\"http://services.rechargevalidationcancellation.digitel.com.ve/\">","").
			   replaceAll("</ns2:executeResponse>","").
			   replaceAll("</S:Body>","").
			   replaceAll("</S:Envelope>","");
	   RechargeValidationCancellationResponse response=(RechargeValidationCancellationResponse) xstream.fromXML(xml);
	
	return response;
   }
   
   private void prepareXstream(XStream xstream){
	   xstream.alias("RechargeValidationCancellationRequest", RechargeValidationCancellationRequest.class);
	   xstream.alias("RechargeValidationCancellationResponse", RechargeValidationCancellationResponse.class);
	   xstream.alias("customerOrderItem",CustomerOrderItem.class);
	   xstream.alias("functionalMessage",FunctionalMessage.class);
	   xstream.alias("customerAccountInteractionRole",CustomerAccountInteractionRole.class);
	   xstream.alias("payment",Payment.class);
	   xstream.addImplicitCollection(CustomerOrder.class,"customerOrderItem","customerOrderItem",CustomerOrderItem.class);
	   xstream.addImplicitCollection(CustomerOrderItem.class,"customerAccountInteractionRole","customerAccountInteractionRole",CustomerAccountInteractionRole.class);
	   xstream.addImplicitCollection(CustomerAccount.class,"payment","payment",Payment.class);
	   xstream.addImplicitCollection(PartyInteractionRole.class,"partyRole","partyRole",PartyRole.class);
	   xstream.registerConverter(new CalendarConverter());
   }
   
   private String enmascarar(String cadena) throws Exception {
	    short nDigit=Short.parseShort(AppProperties.getProperty("RECHARGEVALIDATIONCANCELLATION_NDIGITB"));
	    char[] chars = new char[nDigit];
	    Arrays.fill(chars, AppProperties.getProperty("RECHARGEVALIDATIONCANCELLATION_DIGITM").charAt(0));
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