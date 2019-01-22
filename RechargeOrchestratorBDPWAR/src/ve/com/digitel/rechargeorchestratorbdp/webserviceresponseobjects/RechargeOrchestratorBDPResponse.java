package ve.com.digitel.rechargeorchestratorbdp.webserviceresponseobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import ve.com.digitel.rechargeorchestratorbdp.webserviceobject.CustomerOrderResponse;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="RechargeOrchestratorBDPResponse",
propOrder={"customerOrder"})
public class RechargeOrchestratorBDPResponse {
	
	
	@XmlElement(required=true)
	protected CustomerOrderResponse customerOrder;
	
	
	public CustomerOrderResponse getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(CustomerOrderResponse customerOrder) {
		this.customerOrder = customerOrder;
	}

	@Override
	public String toString() {
		return "RechargeOrchestratorBDPResponse [customerOrder="
				+ customerOrder + "]";
	}
	
	
	
}
