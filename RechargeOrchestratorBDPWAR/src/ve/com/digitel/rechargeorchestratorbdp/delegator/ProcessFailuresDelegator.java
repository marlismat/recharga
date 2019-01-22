package ve.com.digitel.rechargeorchestratorbdp.delegator;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import ve.com.digitel.bssint.log.BSSIntLogger;
import ve.com.digitel.framework.components.AbstractAction;
import ve.com.digitel.key.Key;
import ve.com.digitel.rechargeorchestratorbdp.bean.BusinessBean;
import ve.com.digitel.rechargeorchestratorbdp.exception.FunctionalException;
import ve.com.digitel.rechargeorchestratorbdp.util.AppProperties;
import ve.com.digitel.rechargeorchestratorbdp.util.XStreamFactory;
import ve.com.digitel.rechargeorchestratorbdp.webserviceobject.CustomerOrderResponse;
import ve.com.digitel.rechargeorchestratorbdp.webserviceobject.FunctionalMessage;
import ve.com.digitel.rechargeorchestratorbdp.webservicerequestobjects.RechargeOrchestratorBDPRequest;
import ve.com.digitel.rechargeorchestratorbdp.webserviceresponseobjects.RechargeOrchestratorBDPResponse;

public class ProcessFailuresDelegator extends AbstractAction {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4123273410173325447L;
	private static final Logger logger = BSSIntLogger.getBSSIntLogger(ProcessFailuresDelegator.class);
	
	public ProcessFailuresDelegator(String name) {
		super(name);
	}

	public void init() {
		
		logger.info("Inicializando : " + this.getClass().getName());
	}
	
	@Override
	protected void doExecute(Map<Key, Object> context) {
		
		
		
		RechargeOrchestratorBDPResponse response = null;
		RechargeOrchestratorBDPRequest request = null;
		BusinessBean businessBean =null;
		Exception exception=null;
			 
		try {
			
			MDC.put("traceid", (String)context.get(Key.TRACE_ID));
			businessBean = (BusinessBean)context.get(Key.NOT_GSM);
			System.out.println("doExecute");
			request = (RechargeOrchestratorBDPRequest) context.get(Key.REQUEST);
			exception =(Exception) context.get(Key.EXCEPTION);
			response = makeResponse(businessBean,request,exception);
								
		} catch (Exception e) {
			logger.error("Error en la implementacion del servicio PaymentFacadeBanesco, Razon: ",e);
		}
		
		if(logger.isDebugEnabled()){
			logger.debug("Response del servicio PaymentFacadeBanesco: "+XStreamFactory.makeXStream().toXML(response));
		}
		
		context.put(Key.RESPONSE, response);
	
	}
	
	private RechargeOrchestratorBDPResponse makeResponse(BusinessBean businessBean,RechargeOrchestratorBDPRequest request,Exception exception) throws Exception {
		RechargeOrchestratorBDPResponse response=new RechargeOrchestratorBDPResponse();
		
		response.setCustomerOrder(new CustomerOrderResponse());
		response.getCustomerOrder().setId(request.getCustomerOrder().getId());
		response.getCustomerOrder().setOrderNumber(request.getCustomerOrder().getOrderNumber());
		response.getCustomerOrder().setFunctionalMessage(new FunctionalMessage());
		putMessage(response.getCustomerOrder().getFunctionalMessage(), exception);
		return response;
	}
	
	private void putMessage(FunctionalMessage functionalMessage,Exception exception)throws Exception {
			if(exception instanceof FunctionalException){
				functionalMessage.setCode(((FunctionalException) exception).getCodeE());
				functionalMessage.setMessage(((FunctionalException) exception).getMessageE());
			}else{
				functionalMessage.setCode(AppProperties.getMessageProperty("E_MESSAGE01")[0]);
				functionalMessage.setMessage(AppProperties.getMessageProperty("E_MESSAGE01")[1]);	
			}
	}
}