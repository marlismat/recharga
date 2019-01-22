package ve.com.digitel.rechargeorchestratorbdp.webserviceobject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Payment", propOrder = {
    "amount",
    "statusId",
    "paymentDate",
    "ip"
})
public class Payment {
		
	@XmlElement(required = true)
	protected String amount;
	
	@XmlElement(required = true)
	protected String statusId;
	
	@XmlElement(required = true)
	protected PaymentDate paymentDate;
	
	@XmlElement(required = true)
	protected String ip;

	

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public PaymentDate getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(PaymentDate paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public String toString() {
		return "Payment [amount=" + amount + ", statusId=" + statusId
				+ ", paymentDate=" + paymentDate + ", ip=" + ip + "]";
	}
	
	
	
}
