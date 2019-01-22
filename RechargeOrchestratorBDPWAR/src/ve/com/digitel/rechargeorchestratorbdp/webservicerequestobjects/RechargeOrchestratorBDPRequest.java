package ve.com.digitel.rechargeorchestratorbdp.webservicerequestobjects;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import ve.com.digitel.rechargeorchestratorbdp.webserviceobject.CustomerOrderRequest;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="RechargeOrchestratorBDPRequest",propOrder={
		"customerOrder"
})
public class RechargeOrchestratorBDPRequest {
	
	@XmlElement(required=true)
	protected CustomerOrderRequest customerOrder;

	public CustomerOrderRequest getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(CustomerOrderRequest customerOrder) {
		this.customerOrder = customerOrder;
	}

	@Override
	public String toString() {
		return "RechargeOrchestratorBDPRequest [customerOrder=" + customerOrder
				+ "]";
	}
	
	
}