package ve.com.digitel.rechargeorchestratorbdp.proxy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

import ve.com.digitel.bssint.log.BSSIntLogger;
import ve.com.digitel.paymentfacade.util.CalendarConverter;
import ve.com.digitel.paymentfacade.webserviceobject.CreditCard;
import ve.com.digitel.paymentfacade.webserviceobject.CustomerOrderItemRequest;
import ve.com.digitel.paymentfacade.webserviceobject.CustomerOrderItemResponse;
import ve.com.digitel.paymentfacade.webserviceobject.CustomerOrderRequest;
import ve.com.digitel.paymentfacade.webserviceobject.CustomerOrderResponse;
import ve.com.digitel.paymentfacade.webserviceobject.FunctionalMessage;
import ve.com.digitel.paymentfacade.webserviceobject.Holder;
import ve.com.digitel.paymentfacade.webserviceobject.Operation;
import ve.com.digitel.paymentfacade.webserviceobject.OperationDate;
import ve.com.digitel.paymentfacade.webserviceobject.Party;
import ve.com.digitel.paymentfacade.webserviceobject.PartyInteractionRole;
import ve.com.digitel.paymentfacade.webserviceobject.PartyRole;
import ve.com.digitel.paymentfacade.webservicerequestobjects.PaymentFacadeRequest;
import ve.com.digitel.paymentfacade.webserviceresponseobjects.PaymentFacadeResponse;
import ve.com.digitel.rechargeorchestratorbdp.bean.OperationBean;
import ve.com.digitel.rechargeorchestratorbdp.exception.ProxyPaymentFacadeBanescoException;
import ve.com.digitel.rechargeorchestratorbdp.util.AppProperties;
import ve.com.digitel.rechargeorchestratorbdp.webserviceobject.BankResponse;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.StringConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;




public class PaymentFacadeBanescoHttpClient {
	
	private static Logger logger= BSSIntLogger.getBSSIntLogger(PaymentFacadeBanescoHttpClient.class);
	private static HttpClient   httpClient= null;
	private String endPointURL;		
	private String timeOutHttp;
	private String respOk;
	private String httpRespOk;
	private String contentType;
	private String contentEncoding;
	
	private static List<String> ApplyingCancellationCode;
		
			
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
		PaymentFacadeBanescoHttpClient.httpClient = httpClient;
	}
	
	/**
	 * 
	 */
	public void init () throws Exception {
		logger.info("Cargado el delegador: ".concat(this.getClass().getSimpleName()));
		ApplyingCancellationCode=Arrays.asList(AppProperties.getProperty("APPLYING_CANCELLATION_CODE").split(";"));
	}	
	

	public void makeOperation(OperationBean operation,String interactionDate,String orderNumber,String id,Map<String,String> parameters) throws Exception{
	 
	    String resultContents = null;
		String contents       = null;
		PostMethod method =null;
		HttpClient httpClient = null;
		int statusCode=0;
		boolean isError=false;
						
		try{    
			    String voucherTag=AppProperties.getProperty("PAYMENTFACADEBANESCO_PARAM_VOUCHER");
				httpClient = new HttpClient();
				httpClient.getParams().setParameter("http.connection.timeout", new Integer(getTimeOutHttp()));
				method= new PostMethod(getEndPointURL());
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
					logger.error("Ocurrio un error en la invocación al servicio PaymentFacadeBanesco ");
					logger.error("Codigo que devuelve el metodo POST: "+statusCode);
				}
				
				if(!isError){				
					contents= method.getResponseBodyAsString();
					resultContents= contents.toString();
					if(logger.isDebugEnabled()){
						logger.debug("Response: "+resultContents);
						
					}
					PaymentFacadeResponse response=parseResponse(resultContents,voucherTag);
					if(response.getFunctionalMessage().getCode().equalsIgnoreCase(getRespOk())){
						if(response.getCustomerOrder().getCustomerOrderItem().get(0).getBankResponse()!=null){
							operation.setBankResponse(createBankResponse(response.getCustomerOrder().getCustomerOrderItem().get(0).getBankResponse()));
							if(!operation.getBankResponse().getCode().equalsIgnoreCase(AppProperties.getProperty("PAYMENTFACADEBANESCO_BANK_RESP_OK"))){
								isError=true;
								logger.error("Ocurrio un error proveniente del Banco: "); 
								logger.error("Código: "+operation.getBankResponse().getCode());
								logger.error("Mensaje: "+operation.getBankResponse().getMessage());
								operation.setCode(operation.getBankResponse().getCode());
								operation.setMessage(operation.getBankResponse().getMessage());
								if(ApplyingCancellationCode.contains(operation.getCode().trim())){
									operation.setCallCancellation(true);
								}
							}
						}else{
							isError=true;
						}
					}else{
						isError=true;
						operation.setCode(response.getFunctionalMessage().getCode());
						operation.setMessage(response.getFunctionalMessage().getMessage());
						logger.error("Ocurrio un error proveniente del servicio, Código: "+response.getFunctionalMessage().getCode());
						logger.error("Mensaje: "+response.getFunctionalMessage().getMessage());
						logger.error("Código Causa: "+response.getFunctionalMessage().getCodeError());
						logger.error("Mensaje Causa:"+response.getFunctionalMessage().getMessageError());
						
						
						
						
					}
				}
						
			}catch(Exception e){
				logger.error("Error ocurrido durante la invocación al proxy del servicio PaymentFacadeBanesco", e);
				resultContents=null;
				isError=true;
			}finally{				
				httpClient=null;
				method.releaseConnection();
				if(isError){
					throw new ProxyPaymentFacadeBanescoException();
				}
			}
			
	            
	    
   }
	

	private BankResponse createBankResponse(ve.com.digitel.paymentfacade.webserviceobject.BankResponse bankResponse) {
		BankResponse response=new BankResponse();
		response.setApproval(bankResponse.getApproval());
		response.setCode(bankResponse.getCode());
		response.setId(bankResponse.getId());
		response.setMessage(bankResponse.getMessage());
		response.setOrderNumber(bankResponse.getOrderNumber());
		response.setReference(bankResponse.getReference());
		response.setSequence(bankResponse.getSequence());
		response.setSuccess(bankResponse.isSuccess());
		response.setVoucher(bankResponse.getVoucher());
		return response;
	}

	private PaymentFacadeResponse parseResponse(String xml,String voucherTag)throws Exception {
		   XStream xstream=new XStream(new DomDriver());
		   prepareXstream(xstream);
		   xml=xml.replaceAll("<[?]xml version='1.0' encoding='UTF-8'[?]>","").
				   replaceAll("<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">","").
				   replaceAll("<S:Body>","").
				   replaceAll("<ns2:PaymentFacadeBanescoResponse xmlns:ns2=\"http://digitel.com.ve/WSPaymentFacadeBanesco/\">","<PaymentFacadeBanescoResponse>").
				   replaceAll("</ns2:PaymentFacadeBanescoResponse>","</PaymentFacadeBanescoResponse>").
				   replaceAll("</S:Body>","").
				   replaceAll("</S:Envelope>","");
		   String voucher=null;
		   if(xml.indexOf(voucherTag)!=-1){
				voucher=xml.substring(xml.indexOf(voucherTag)+voucherTag.length(),xml.indexOf(voucherTag.replaceAll("<","</")));
				xml=xml.substring(0,xml.indexOf(voucherTag)+voucherTag.length())+xml.substring(xml.indexOf(voucherTag.replaceAll("<","</")),xml.length());
		   }		   
		   PaymentFacadeResponse response=(PaymentFacadeResponse) xstream.fromXML(xml);
		   if(voucher!=null){
			   response.getCustomerOrder().getCustomerOrderItem().get(0).getBankResponse().setVoucher(voucher);
		   }
		   return  response;
	   }
	   
	private void prepareXstream(XStream xstream){
		   xstream.alias("PaymentFacadeBanescoRequest",PaymentFacadeRequest.class);
		   xstream.alias("PaymentFacadeBanescoResponse",PaymentFacadeResponse.class);
		   xstream.alias("functionalMessage",FunctionalMessage.class);
		   xstream.alias("customerOrderItem", CustomerOrderItemRequest.class);
		   xstream.alias("customerOrderItem", CustomerOrderItemResponse.class);
		   xstream.alias("partyRole",PartyRole.class);
		   xstream.addImplicitCollection(CustomerOrderRequest.class,"customerOrderItem","customerOrderItem",CustomerOrderItemRequest.class);
		   xstream.addImplicitCollection(CustomerOrderResponse.class,"customerOrderItem","customerOrderItem",CustomerOrderItemResponse.class);
		   xstream.addImplicitCollection(PartyInteractionRole.class,"partyRole","partyRole",PartyRole.class);
		   xstream.registerConverter(new CalendarConverter());
		   //xstream.registerLocalConverter(ve.com.digitel.paymentfacade.webserviceobject.BankResponse.class,"voucher",new StringConverter());
	}

	private String parseRequest(PaymentFacadeRequest request)throws Exception {
		   String requestXml="";
		   XStream xstream=new XStream();
		   prepareXstream(xstream);
		   requestXml=xstream.toXML(request);
		   
		   requestXml="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:wsp=\"http://digitel.com.ve/WSPaymentFacadeBanesco/\">"+
				   "<soapenv:Header/>"+
				   "<soapenv:Body>"+
			   		requestXml+
				   "</soapenv:Body>"+
				  "</soapenv:Envelope>";
		   
		   requestXml=requestXml.replaceAll("PaymentFacadeBanescoRequest","wsp:PaymentFacadeBanescoRequest");
	      
		   return requestXml;
	}
	   
	   
	@SuppressWarnings("unused")
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

	private PaymentFacadeRequest createRequest(OperationBean operation,String interactionDate,String orderNumber,String id,Map<String,String> parameters) throws Exception {
		   PaymentFacadeRequest request=new  PaymentFacadeRequest();
		   request.setCustomerOrder(new CustomerOrderRequest());
		   request.getCustomerOrder().setId(id);
		   request.getCustomerOrder().setPartyInteractionRole(new PartyInteractionRole());
		   request.getCustomerOrder().getPartyInteractionRole().setPartyRole(new ArrayList<PartyRole>());
		   PartyRole channel=new PartyRole();
		   channel.setName(AppProperties.getProperty("PAYMENTFACADEBANESCO_PARAM_CHANNEL"));
		   channel.setParty(new Party());
		   String channels=parameters.get(AppProperties.getProperty("PAYMENTFACADEBANESCO_PARAM_CHANNEL"));
		   String firstLevel[]=channels.split(AppProperties.getProperty("SEPARATOR"));
		   channel.getParty().setName(firstLevel[1]); 
		   
		   
		   request.getCustomerOrder().getPartyInteractionRole().getPartyRole().add(channel);
		   PartyRole key=new PartyRole();
		   key.setName(AppProperties.getProperty("PAYMENTFACADEBANESCO_PARAM_KEY"));
		   key.setParty(new Party());
		   key.getParty().setName(AppProperties.getProperty("PAYMENTFACADEBANESCO_PARAM_KEY_VALUE"));
		   request.getCustomerOrder().getPartyInteractionRole().getPartyRole().add(key);
		   PartyRole publicKeyId=new PartyRole();
		   publicKeyId.setName(AppProperties.getProperty("PAYMENTFACADEBANESCO_PARAM_PUBLICKEY"));
		   publicKeyId.setParty(new Party());
		   publicKeyId.getParty().setName(AppProperties.getProperty("PAYMENTFACADEBANESCO_PARAM_PUBLICKEY_VALUE"));
		   request.getCustomerOrder().getPartyInteractionRole().getPartyRole().add(publicKeyId);
		   request.getCustomerOrder().setCustomerOrderItem(new ArrayList<CustomerOrderItemRequest>());
		   CustomerOrderItemRequest item=new CustomerOrderItemRequest();
		   item.setId(operation.getId());
		   item.setMsisdn(operation.getBeneficiary());
		   item.setOperation(new Operation());
		   item.getOperation().setType(AppProperties.getProperty("PAYMENTFACADEBANESCO_PARAM_TYPE_OPERATION"));
		   item.getOperation().setAmount(operation.getAmount());
		   item.getOperation().setIp(operation.getIp());
		   item.getOperation().setOrderNumber(orderNumber);
		   item.getOperation().setStatusId(operation.getStatusId());
		   item.getOperation().setDescription(AppProperties.getProperty("PAYMENTFACADEBANESCO_PARAM_TYPE_OPERATION_DESCRIPTION"));
		   OperationDate operationDate=new OperationDate();
		   operationDate.setTime(operation.getOperationDateTime());
		   operationDate.setTimezone(AppProperties.getProperty("TIME_ZONE"));
		   item.getOperation().setOperationDate(operationDate);
		   CreditCard creditCard=new CreditCard();
		   creditCard.setCvc(operation.getCreditCardCvc());
		   creditCard.setExpirationDate(operation.getCreditCardExpirationDate());
		   Holder holder=new Holder();
		   holder.setAddress(operation.getCreditCardHolderAddress());
		   holder.setCity(operation.getCreditCardHolderCity());
		   holder.setId(operation.getCreditCardHolderId());
		   holder.setName(operation.getCreditCardHolderName());
		   holder.setState(operation.getCreditCardHolderState());
		   holder.setZipCode(operation.getCreditCardHolderZipCode());
		   creditCard.setHolder(holder);
		   creditCard.setNumber(operation.getCreditCardNumber());
		   creditCard.setType(operation.getCreditCardType());
		   item.getOperation().setCreditCard(creditCard);
		   request.getCustomerOrder().getCustomerOrderItem().add(item);	   
		   return request;
	}	
}