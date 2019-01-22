package ve.com.digitel.rechargeorchestratorbdp.delegator;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import ve.com.digitel.bssint.log.BSSIntLogger;
import ve.com.digitel.framework.components.AbstractRule;
import ve.com.digitel.key.Key;
import ve.com.digitel.rechargeorchestratorbdp.bean.BusinessBean;
import ve.com.digitel.rechargeorchestratorbdp.bean.OperationBean;
import ve.com.digitel.rechargeorchestratorbdp.exception.RechargeNotCompleted;
import ve.com.digitel.rechargeorchestratorbdp.proxy.PaymentFacadeBanescoHttpClient;
import ve.com.digitel.rechargeorchestratorbdp.proxy.RechargeSolicitudeHttpClient;
import ve.com.digitel.rechargeorchestratorbdp.proxy.RechargeValidationCancellationHttpClient;
import ve.com.digitel.rechargeorchestratorbdp.proxy.RechargeValidationSolicitudeHttpClient;

public class PerformActionDelegator extends AbstractRule {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = BSSIntLogger.getBSSIntLogger(PerformActionDelegator.class);
	
	@Autowired
	private RechargeValidationSolicitudeHttpClient  rechargeValidationSolicitudeHttpClient;
	
	@Autowired
	private PaymentFacadeBanescoHttpClient  paymentFacadeBanescoHttpClient;
	
	@Autowired
	private RechargeValidationCancellationHttpClient  rechargeValidationCancellationHttpClient;
	
	@Autowired
	private RechargeSolicitudeHttpClient rechargeSolicitudeHttpClient; 
	
	

	public PerformActionDelegator(String name) {
		super(name);
	}
	
	public void init() {
		
		logger.info("Inicializando : " + this.getClass().getName());
	}
	
	@Override
	protected boolean makeDecision(Map<Key, Object> context) {
		System.out.println("MAKE DECISION");
		boolean continues = true;
		
		try{
			BusinessBean businessBean=(BusinessBean) context.get(Key.NOT_GSM);
			
		    for(OperationBean operation:businessBean.getOperations()){
		    	if(operation.isValid()){
		    		 makeOperation(operation,businessBean.getInteractionDate(),businessBean.getOrderNumber(),businessBean.getId(),businessBean.getGlobalParameters());
		    		 
		    	}
		    }
		    context.put(Key.NOT_GSM,businessBean);
		
		}catch(Exception e){
			
			continues=false;
			context.put(Key.ERROR,true);
			context.put(Key.EXCEPTION,e);
		}
		
		return continues;	
	}

	private void makeOperation(OperationBean operation,String interactionDate,String orderNumber,String id,Map<String,String> parameters) {
		System.out.println("Make 	Operation");
		boolean error=false;
		try{
			rechargeValidationSolicitudeHttpClient.makeOperation(operation,interactionDate,orderNumber,parameters);
			try{
				logger.info("Se va proceder a realizar la recargar al Banco ");
				paymentFacadeBanescoHttpClient.makeOperation(operation,interactionDate,orderNumber,id,parameters);
				
			}catch(Exception e){
				error=!error;
				if(operation.isCallCancellation()){
					logger.error("Ocurrio un error en la invocación a la fachada de Banesco se va llamar al servicio rechargeValidationCancellation");
					logger.error("PurchaseOrder: "+operation.getPurchaseOrderNumber()+", OrderNumber:"+orderNumber);
					rechargeValidationCancellationHttpClient.makeOperation(operation,interactionDate,orderNumber,id,parameters);
				}
				throw new RechargeNotCompleted();
			}
			
			if(!error){
				logger.info("Se va proceder a procesar la recarga en Digitel ");
				rechargeSolicitudeHttpClient.makeOperation(operation,interactionDate,orderNumber,id,parameters);
			}
					
		}catch(Exception e){
			operation.setValid(false);
			operation.setException(e);
		}
	}
	
}