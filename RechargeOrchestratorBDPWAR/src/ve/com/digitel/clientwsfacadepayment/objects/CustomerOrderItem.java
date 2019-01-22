package ve.com.digitel.clientwsfacadepayment.objects;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"id", "operation", "payment"})
public class CustomerOrderItem implements Serializable {

	/**
	 * Id de la operacion.
	 */	
	@XmlElement(required=true, nillable=false)
	private String id;

	/**
	 * Objeto que agrupa información de la operacion.
	 */	
	@XmlElement(required=true, nillable=false)
	private Operation operation;
	
	/**
	 * Objeto que agrupa información del payment.
	 */	
	@XmlElement(required=false, nillable=false)
	private Payment payment;
	
	public CustomerOrderItem() {
		super();
	}

	public CustomerOrderItem(String id, Operation operation, Payment payment) {
		super();
		this.id = id;
		this.operation = operation;
		this.payment = payment;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	@Override
	public String toString() {
		return "CustomerOrderItem [id=" + id + ", operation=" + operation
				+ ", payment=" + payment + "]";
	}
}