package ve.com.digitel.rechargeorchestratorbdp.delegator;


import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import ve.com.digitel.bssint.log.BSSIntLogger;
import ve.com.digitel.framework.components.AbstractAction;
import ve.com.digitel.key.Key;
import ve.com.digitel.rechargeorchestratorbdp.bean.BusinessBean;
import ve.com.digitel.rechargeorchestratorbdp.bean.OperationBean;
import ve.com.digitel.rechargeorchestratorbdp.exception.FunctionalException;
import ve.com.digitel.rechargeorchestratorbdp.util.AppProperties;
import ve.com.digitel.rechargeorchestratorbdp.util.XStreamFactory;
import ve.com.digitel.rechargeorchestratorbdp.webserviceobject.PaymentCardResponse;
import ve.com.digitel.rechargeorchestratorbdp.webserviceobject.CustomerOrderItemResponse;
import ve.com.digitel.rechargeorchestratorbdp.webserviceobject.CustomerOrderResponse;
import ve.com.digitel.rechargeorchestratorbdp.webserviceobject.FunctionalMessage;
import ve.com.digitel.rechargeorchestratorbdp.webservicerequestobjects.RechargeOrchestratorBDPRequest;
import ve.com.digitel.rechargeorchestratorbdp.webserviceresponseobjects.RechargeOrchestratorBDPResponse;

public class SendMessageApprovalDelegator extends AbstractAction {

	
	
	private static final long serialVersionUID = -8815936255526462091L;
	private static final Logger logger = BSSIntLogger.getBSSIntLogger(SendMessageApprovalDelegator.class);
	
	public SendMessageApprovalDelegator(String name) {
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
			 
		try {
			
			MDC.put("traceid", (String)context.get(Key.TRACE_ID));
			businessBean = (BusinessBean)context.get(Key.NOT_GSM);
			request = (RechargeOrchestratorBDPRequest) context.get(Key.REQUEST);
			
			response = makeResponse(businessBean,request);
								
		} catch (Exception e) {
			logger.error("Error en la implementacion del servicio RechargeOrchestratorBDP, Razon: ",e);
		}
		
		if(logger.isDebugEnabled()){
			logger.debug("Response del servicio RechargeOrchestratorBDP: "+XStreamFactory.makeXStream().toXML(response));
		}
		
		context.put(Key.RESPONSE, response);
			
	}

	private RechargeOrchestratorBDPResponse makeResponse(BusinessBean businessBean,RechargeOrchestratorBDPRequest request) throws Exception {
		RechargeOrchestratorBDPResponse response=new RechargeOrchestratorBDPResponse();
		response.setCustomerOrder(new CustomerOrderResponse());
		response.getCustomerOrder().setFunctionalMessage(new FunctionalMessage());
		response.getCustomerOrder().getFunctionalMessage().setCode(AppProperties.getMessageProperty("F_MESSAGE00")[0]);
		response.getCustomerOrder().getFunctionalMessage().setMessage(AppProperties.getMessageProperty("F_MESSAGE00")[1]);
		response.getCustomerOrder().setOrderNumber(businessBean.getOrderNumber());
		response.getCustomerOrder().setPartyInteractionRole(request.getCustomerOrder().getPartyInteractionRole());
		response.getCustomerOrder().setId(businessBean.getId());
		response.getCustomerOrder().setCustomerOrderItem(new ArrayList<CustomerOrderItemResponse>());
		response.getCustomerOrder().setInteractionDate(businessBean.getInteractionDate());
		CustomerOrderItemResponse itemResponse;
		short ind=-1;
		for(OperationBean item:businessBean.getOperations()){
			ind++;
			itemResponse=new CustomerOrderItemResponse();
			itemResponse.setBankResponse(item.getBankResponse());
			itemResponse.setFunctionalMessage(new FunctionalMessage());
			if(item.isValid()){
				itemResponse.getFunctionalMessage().setCode(AppProperties.getMessageProperty("F_MESSAGE00_00")[0]);
				itemResponse.getFunctionalMessage().setMessage(AppProperties.getMessageProperty("F_MESSAGE00_00")[1]);
			}else{
				if(item.getException() instanceof FunctionalException){
					if(item.getCode()==null&&item.getMessage()==null){
						itemResponse.getFunctionalMessage().setCode(((FunctionalException) item.getException()).getCodeE());
						itemResponse.getFunctionalMessage().setMessage(((FunctionalException) item.getException()).getMessageE());
					}else{
						itemResponse.getFunctionalMessage().setCode(item.getCode());
						itemResponse.getFunctionalMessage().setMessage(item.getMessage());
					}
				}else{
					itemResponse.getFunctionalMessage().setCode(AppProperties.getMessageProperty("E_MESSAGE01")[0]);
					itemResponse.getFunctionalMessage().setMessage(AppProperties.getMessageProperty("E_MESSAGE01")[1]);
				}	
			}
			itemResponse.setId(item.getId());
			itemResponse.setCustomerAccountInteractionRole(request.getCustomerOrder().getCustomerOrderItem().get(ind).getCustomerAccountInteractionRole());
			itemResponse.setPayment(request.getCustomerOrder().getCustomerOrderItem().get(ind).getPayment());
			itemResponse.setPaymentCard(new PaymentCardResponse());
			itemResponse.getPaymentCard().setAlias(item.getCreditCardAlias());
			response.getCustomerOrder().getCustomerOrderItem().add(itemResponse);
		}
		
		return response;
	}


}