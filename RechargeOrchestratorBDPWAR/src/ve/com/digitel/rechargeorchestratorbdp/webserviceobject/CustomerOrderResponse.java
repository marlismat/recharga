package ve.com.digitel.rechargeorchestratorbdp.webserviceobject;


import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerOrderResponse", propOrder = {
		"id",
	    "orderNumber",
	    "interactionDate",
	    "customerOrderItem",
	    "partyInteractionRole",
	    "functionalMessage"
})
public class CustomerOrderResponse {
	
	
	@XmlElement( required = true)
	protected String id;
	
	@XmlElement(required = true)
	protected String orderNumber;
	
	@XmlElement(required = true)
	protected String interactionDate;
	
	@XmlElement(name = "customerOrderItem", required = true)
	protected List<CustomerOrderItemResponse> customerOrderItem;
	
	@XmlElement(name = "partyInteractionRole", required = true)
	protected PartyInteractionRole partyInteractionRole;
	
	@XmlElement(name = "functionalMessage", required = true)
	protected FunctionalMessage functionalMessage;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getInteractionDate() {
		return interactionDate;
	}

	public void setInteractionDate(String interactionDate) {
		this.interactionDate = interactionDate;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public List<CustomerOrderItemResponse> getCustomerOrderItem() {
		return customerOrderItem;
	}

	public void setCustomerOrderItem(
			List<CustomerOrderItemResponse> customerOrderItem) {
		this.customerOrderItem = customerOrderItem;
	}

	public PartyInteractionRole getPartyInteractionRole() {
		return partyInteractionRole;
	}

	public void setPartyInteractionRole(PartyInteractionRole partyInteractionRole) {
		this.partyInteractionRole = partyInteractionRole;
	}

	public FunctionalMessage getFunctionalMessage() {
		return functionalMessage;
	}

	public void setFunctionalMessage(FunctionalMessage functionalMessage) {
		this.functionalMessage = functionalMessage;
	}

}
