package ve.com.digitel.rechargeorchestratorbdp.webservice;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.servlet.ServletContext;
import javax.xml.ws.BindingType;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ve.com.digitel.bssint.log.BSSIntLogger;
//import ve.com.digitel.paymentfacade.business.BusinessImplementation;
//import ve.com.digitel.paymentfacade.webservicerequestobjects.PaymentFacadeRequest;
//import ve.com.digitel.paymentfacade.webserviceresponseobjects.PaymentFacadeResponse;
import ve.com.digitel.rechargeorchestratorbdp.business.BusinessImplementation;
import ve.com.digitel.rechargeorchestratorbdp.webservicerequestobjects.RechargeOrchestratorBDPRequest;
import ve.com.digitel.rechargeorchestratorbdp.webserviceresponseobjects.RechargeOrchestratorBDPResponse;


@WebService(portName="RechargeOrchestratorBDPSOAP", serviceName="RechargeOrchestratorBDP",
targetNamespace="http://digitel.com.ve/RechargeOrchestratorBDP/",
endpointInterface="ve.com.digitel.rechargeorchestratorbdp.webservice.RechargeOrchestratorBDP")
@BindingType("http://schemas.xmlsoap.org/wsdl/soap/http")


public class RechargeOrchestratorBDPSOAPImpl implements RechargeOrchestratorBDP{

	private static final Logger logger = BSSIntLogger.getBSSIntLogger(RechargeOrchestratorBDPSOAPImpl.class);

	@Resource(name="wsContext")
	private WebServiceContext wsContext;

	private WebApplicationContext getApplicationContext(){
		
		MessageContext msgContext = wsContext.getMessageContext();
		ServletContext context = (ServletContext) msgContext.get(MessageContext.SERVLET_CONTEXT);
		WebApplicationContext webContext = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		return webContext;
	}

	@Override
	public RechargeOrchestratorBDPResponse makeOperation(RechargeOrchestratorBDPRequest request) {
		WebApplicationContext context;
		RechargeOrchestratorBDPResponse response = null;
		logger.info("Iniciando transaccion en el servicio RechargeOrchestratorBDP");	 
		try {
			context=getApplicationContext();
			response=context.getBean(BusinessImplementation.class).execute(request);
		} catch (Exception e) {
			logger.log(Level.ERROR, e.toString());
		}
		logger.info("Finalizando transaccion en el servicio RechargeOrchestratorBDP");

		return response;
	}
}
