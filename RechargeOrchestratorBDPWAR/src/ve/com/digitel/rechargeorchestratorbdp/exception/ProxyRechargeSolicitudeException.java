package ve.com.digitel.rechargeorchestratorbdp.exception;

import java.io.Serializable;

public class ProxyRechargeSolicitudeException extends FunctionalException implements Serializable{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 7368180852195131728L;

	public ProxyRechargeSolicitudeException() {
		super("F_MESSAGE_23");
		
	}

}
