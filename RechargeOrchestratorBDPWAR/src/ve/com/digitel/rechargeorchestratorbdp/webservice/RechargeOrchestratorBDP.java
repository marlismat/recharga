package ve.com.digitel.rechargeorchestratorbdp.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import ve.com.digitel.rechargeorchestratorbdp.webservicerequestobjects.RechargeOrchestratorBDPRequest;
import ve.com.digitel.rechargeorchestratorbdp.webserviceresponseobjects.RechargeOrchestratorBDPResponse;






@WebService(name="RechargeOrchestratorBDP", targetNamespace="http://digitel.com.ve/RechargeOrchestratorBDP/")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)

public interface RechargeOrchestratorBDP {
	
	@WebMethod(operationName="recharge", action="http://digitel.com.ve/RechargeOrchestratorBDP/RechargeOrchestratorBDP")
	@WebResult(name="RechargeOrchestratorBDPResponse", targetNamespace="http://digitel.com.ve/RechargeOrchestratorBDP/", partName="parameters")
	public RechargeOrchestratorBDPResponse makeOperation(
			@WebParam(name="RechargeOrchestratorBDPRequest", targetNamespace="http://digitel.com.ve/RechargeOrchestratorBDP/",partName="parameters" )
			RechargeOrchestratorBDPRequest request);
}