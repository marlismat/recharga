package ve.com.digitel.rechargeorchestratorbdp.business;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import ve.com.digitel.bssint.log.BSSIntLogger;
import ve.com.digitel.clientwsfacadepayment.connection.ServisConectPayment;
import ve.com.digitel.clientwsfacadepayment.objects.CreditCard;
import ve.com.digitel.clientwsfacadepayment.objects.CustomerOrder;
import ve.com.digitel.clientwsfacadepayment.objects.CustomerOrderItem;
import ve.com.digitel.clientwsfacadepayment.objects.Holder;
import ve.com.digitel.clientwsfacadepayment.objects.Operation;
import ve.com.digitel.clientwsfacadepayment.objects.WSFacadePaymentRequest;
import ve.com.digitel.clientwsfacadepayment.objects.WSFacadePaymentResponse;
import ve.com.digitel.framework.components.AbstractComponent;
import ve.com.digitel.key.Key;
import ve.com.digitel.rechargeorchestratorbdp.util.AppProperties;
import ve.com.digitel.rechargeorchestratorbdp.util.XStreamFactory;
import ve.com.digitel.rechargeorchestratorbdp.webservicerequestobjects.RechargeOrchestratorBDPRequest;
import ve.com.digitel.rechargeorchestratorbdp.webserviceresponseobjects.RechargeOrchestratorBDPResponse;


public class BusinessImplementation implements Serializable {
	
	private static final long serialVersionUID = -7372416855288319517L;

	private static final Logger logger = BSSIntLogger.getBSSIntLogger(BusinessImplementation.class);
	
	private AbstractComponent businessRules = null;

	public AbstractComponent getBusinessRules() {
		return businessRules;
	}

	public void setBusinessRules(AbstractComponent businessRules) {
		this.businessRules = businessRules;
	}
	
	public void init() {
		logger.info("Inicializando : " + this.getClass().getName());
	}
	
	public WSFacadePaymentResponse execute_client() {
		/*Cliente WSFacadePayment*/
		WSFacadePaymentRequest request_client = new WSFacadePaymentRequest();
		WSFacadePaymentResponse response_client = new WSFacadePaymentResponse();
		ServisConectPayment scp = new ServisConectPayment();
		
		CustomerOrder co = new CustomerOrder();
		CustomerOrderItem coi = new CustomerOrderItem();
		CreditCard credit_card = new CreditCard();
		Holder holder = new Holder();
		Operation operation = new Operation();
		
		holder.setId("V123654");
		holder.setName("Ana A. Perez");
		
		credit_card.setCvv("422");
		credit_card.setExpirationDate("0519");
		credit_card.setHolder(holder);
		credit_card.setNumber("5406282515946732");
		credit_card.setType("");
		
		operation.setAmount("1200000");
		operation.setCreditCard(credit_card);
		operation.setIdcomercio("2712201701");
		operation.setInvoice("01010020");
		operation.setMode("");
		operation.setOrderNumber("");
		operation.setTerminal("");
		operation.setTraceid("");
		operation.setType("compra");
		coi.setOperation(operation);
		co.setCustomerOrderItem(coi);
		request_client.setCustomerOrder(co);
		
		try {
			response_client = scp.ws_facade_payment(request_client);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error in ExecuteClient: "+ e.getMessage());
			System.out.println("Couse of error in ExecuteClient: "+ e.getCause());
			e.printStackTrace();
		}
		
		System.out.println("Response WSFacadePayment: "+response_client.toString());
		//return response_client;
		return null;
		/*Cliente WSFacadePayment*/	
	}
	
	public RechargeOrchestratorBDPResponse execute(RechargeOrchestratorBDPRequest request) {
		
		Map<Key, Object> context = new HashMap<Key, Object>();
		
		//this.execute_client();
		String traceId = request.getCustomerOrder().getId()!=null&&!request.getCustomerOrder().getId().isEmpty()?
				request.getCustomerOrder().getId():AppProperties.getProperty("PREFIJO_TRACE_ID");
				
		traceId+="_"+System.currentTimeMillis();
				
		MDC.put("traceid",traceId);
		context.put(Key.TRACE_ID,traceId);
		context.put(Key.REQUEST, request);
		context.put(Key.ERROR, false);
		
		System.out.println("XXD 22 asdasdas asdasd  asasda");   
		logger.info("Iniciando llamado al metodo execute de  " + this.getClass().getName());
		/*if(logger.isDebugEnabled()){	
			logger.debug("Datos de Entrada del Request: "+XStreamFactory.makeXStream().toXML(request)); 
		}*/
		businessRules.execute(context);
		logger.info("Finalizando llamado al metodo execute de  " + this.getClass().getName());
		return (RechargeOrchestratorBDPResponse)context.get(Key.RESPONSE);
	}
}