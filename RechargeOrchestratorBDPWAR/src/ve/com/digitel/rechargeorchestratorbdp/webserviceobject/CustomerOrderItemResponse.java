package ve.com.digitel.rechargeorchestratorbdp.webserviceobject;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerOrderItemResponse", propOrder = {
		"id",
	    "customerAccountInteractionRole",
	    "payment",
	    "paymentCard",
	    "bankResponse",
	    "functionalMessage"
})
public class CustomerOrderItemResponse {

	@XmlElement(required = true)
	protected String id;
	
	@XmlElement(required = true)
	protected List<CustomerAccountInteractionRole> customerAccountInteractionRole;
	
	@XmlElement(required = true)
	protected Payment payment;
	
	@XmlElement(required = true)
	protected PaymentCardResponse paymentCard;
	
	@XmlElement(required = true)
	protected BankResponse bankResponse;
	
	@XmlElement(required = true)
	protected FunctionalMessage functionalMessage;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<CustomerAccountInteractionRole> getCustomerAccountInteractionRole() {
		return customerAccountInteractionRole;
	}

	public void setCustomerAccountInteractionRole(
			List<CustomerAccountInteractionRole> customerAccountInteractionRole) {
		this.customerAccountInteractionRole = customerAccountInteractionRole;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public PaymentCardResponse getPaymentCard() {
		return paymentCard;
	}

	public void setPaymentCard(PaymentCardResponse paymentCard) {
		this.paymentCard = paymentCard;
	}

	public BankResponse getBankResponse() {
		return bankResponse;
	}

	public void setBankResponse(BankResponse bankResponse) {
		this.bankResponse = bankResponse;
	}

	public FunctionalMessage getFunctionalMessage() {
		return functionalMessage;
	}

	public void setFunctionalMessage(FunctionalMessage functionalMessage) {
		this.functionalMessage = functionalMessage;
	}
	
	
	
	
}