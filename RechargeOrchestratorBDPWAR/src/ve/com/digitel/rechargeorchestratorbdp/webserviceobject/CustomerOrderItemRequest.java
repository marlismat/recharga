package ve.com.digitel.rechargeorchestratorbdp.webserviceobject;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerOrderItemRequest", propOrder = {
    "id",
    "type",
    "customerAccountInteractionRole",
    "payment",
    "formOfPay",
    "paymentCard"
})
public class CustomerOrderItemRequest {

	@XmlElement(required = true)
	protected String id;
	
	@XmlElement(required = true)
	protected String type;
	
	@XmlElement(required = true)
	protected List<CustomerAccountInteractionRole> customerAccountInteractionRole;
	
	@XmlElement(required = true)
	protected Payment payment;
	
	@XmlElement(required = true,name="paymentCard")
	protected PaymentCard paymentCard;
	
	@XmlElement(required = true)
	protected String formOfPay;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getFormOfPay() {
		return formOfPay;
	}

	public void setFormOfPay(String formOfPay) {
		this.formOfPay = formOfPay;
	}

	public PaymentCard getPaymentCard() {
		return paymentCard;
	}

	public void setPaymentCard(PaymentCard paymentCard) {
		this.paymentCard = paymentCard;
	}

	@Override
	public String toString() {
		return "CustomerOrderItemRequest [id=" + id + ", type=" + type
				+ ", customerAccountInteractionRole="
				+ customerAccountInteractionRole + ", payment=" + payment
				+ ", paymentCard=" + paymentCard + ", formOfPay=" + formOfPay
				+ "]";
	}
	
	

}