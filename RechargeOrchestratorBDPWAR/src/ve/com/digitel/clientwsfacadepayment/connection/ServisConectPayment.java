package ve.com.digitel.clientwsfacadepayment.connection;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ve.com.digitel.bssint.log.BSSIntLogger;
import ve.com.digitel.clientwsfacadepayment.objects.CustomerOrderItem;
import ve.com.digitel.clientwsfacadepayment.objects.EncripterServisResponse;
import ve.com.digitel.clientwsfacadepayment.objects.EncripterServisResquest;
import ve.com.digitel.clientwsfacadepayment.objects.EncripterUtils;
import ve.com.digitel.clientwsfacadepayment.objects.ValidateRule;
import ve.com.digitel.clientwsfacadepayment.objects.WSFacadePaymentRequest;
import ve.com.digitel.clientwsfacadepayment.objects.WSFacadePaymentResponse;
import ve.com.digitel.rechargeorchestratorbdp.util.AppProperties;

public class ServisConectPayment {
	
	public static final Logger log = BSSIntLogger.getBSSIntLogger(ServisConectPayment.class);
	public static AppProperties properties;
	
	public static WSFacadePaymentResponse ws_facade_payment (WSFacadePaymentRequest wsfr) throws Exception{
		JaxbApiGold.setObj(null);	
		WSFacadePaymentRequest Request = wsfr;
		
		Object obj = new WSFacadePaymentResponse();
		obj = JaxbApiGold.execute(	true, 				
				properties.getProperty("USUARIO"),
				properties.getProperty("CONTRASENA"),
				properties.getProperty("URL_WS_FACADE_PAYMENT")+"?wsdl",
				properties.getProperty("URL_WS_FACADE_PAYMENT"),
				Request,
				"ser:executeWSFacadePayment",
				WSFacadePaymentRequest.class,
				WSFacadePaymentResponse.class,
				"wsfacadepayment",
				"xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.businessrulevalidatorservice.digitel.com.ve/\"",
				obj,
				null,
				"wsfacadepayment"
			);	
		return ( WSFacadePaymentResponse ) obj;	
		
	}
	
	}