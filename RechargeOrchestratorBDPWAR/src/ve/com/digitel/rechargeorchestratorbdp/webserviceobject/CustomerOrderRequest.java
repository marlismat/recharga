package ve.com.digitel.rechargeorchestratorbdp.webserviceobject;


import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerOrderRequest", propOrder = {
    "id",
    "orderNumber",
    "interactionDate",
    "customerOrderItem",
    "partyInteractionRole"
})
public class CustomerOrderRequest {
	
	
	@XmlElement( required = true)
	protected String id;
	
	@XmlElement( required = true)
	protected String orderNumber;
	
	@XmlElement(required = true)
	protected String interactionDate;
	
	@XmlElement(name = "customerOrderItem", required = true)
	protected List<CustomerOrderItemRequest> customerOrderItem;
	
	@XmlElement(name= "partyInteractionRole",required = true)
	protected PartyInteractionRole partyInteractionRole;
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
		
	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public PartyInteractionRole getPartyInteractionRole() {
		return partyInteractionRole;
	}

	public void setPartyInteractionRole(PartyInteractionRole partyInteractionRole) {
		this.partyInteractionRole = partyInteractionRole;
	}

	public List<CustomerOrderItemRequest> getCustomerOrderItem() {
		return customerOrderItem;
	}

	public void setCustomerOrderItem(
			List<CustomerOrderItemRequest> customerOrderItem) {
		this.customerOrderItem = customerOrderItem;
	}

	public String getInteractionDate() {
		return interactionDate;
	}

	public void setInteractionDate(String interactionDate) {
		this.interactionDate = interactionDate;
	}

	@Override
	public String toString() {
		return "CustomerOrderRequest [id=" + id + ", orderNumber="
				+ orderNumber + ", interactionDate=" + interactionDate
				+ ", customerOrderItem=" + customerOrderItem.toString()
				+ ", partyInteractionRole=" + partyInteractionRole + "]";
	}
	
	
	
	
	
}
