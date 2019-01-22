package ve.com.digitel.rechargeorchestratorbdp.exception;

import ve.com.digitel.rechargeorchestratorbdp.util.AppProperties;

public class FunctionalException extends Exception {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7767302338178456691L;
	private String codeE;
	private String messageE;
	
	public FunctionalException(String tokenPropertie){
		codeE=AppProperties.getMessageProperty(tokenPropertie)[0];
		messageE=AppProperties.getMessageProperty(tokenPropertie)[1];
	}

	public String getCodeE() {
		return codeE;
	}

	public void setCodeE(String codeE) {
		this.codeE = codeE;
	}

	public String getMessageE() {
		return messageE;
	}

	public void setMessageE(String messageE) {
		this.messageE = messageE;
	}

}
