package ve.com.digitel.rechargeorchestratorbdp.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class BusinessBean implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -860456819961774312L;

	private boolean valid;
	
	private List<OperationBean> operations;
	
	private Map<String,String> globalParameters;
	
	private String id;
	
	private String orderNumber;
	
	private String interactionDate;
	
	
	public Map<String, String> getGlobalParameters() {
		return globalParameters;
	}

	public void setGlobalParameters(Map<String, String> globalParameters) {
		this.globalParameters = globalParameters;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public List<OperationBean> getOperations() {
		return operations;
	}

	public void setOperations(List<OperationBean> operations) {
		this.operations = operations;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getInteractionDate() {
		return interactionDate;
	}

	public void setInteractionDate(String interactionDate) {
		this.interactionDate = interactionDate;
	}

	@Override
	public String toString() {
		return "BusinessBean [valid=" + valid + ", operations=" + operations
				+ ", globalParameters=" + globalParameters.toString() + ", id=" + id
				+ ", orderNumber=" + orderNumber + ", interactionDate="
				+ interactionDate + "]";
	}
}
