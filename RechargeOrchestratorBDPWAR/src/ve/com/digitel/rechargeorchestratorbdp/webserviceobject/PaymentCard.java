package ve.com.digitel.rechargeorchestratorbdp.webserviceobject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentCard", propOrder = {
    "number",
    "cvc",
    "expirationDate",
    "alias",
    "type",
    "holder"
})
public class PaymentCard {
	
	@XmlElement(required = true)
	protected String number;
	
	@XmlElement(required = true)
	protected String cvc;
	
	@XmlElement(required = true)
	protected String expirationDate;
	
	@XmlElement(required = false)
	protected String alias;
	
	@XmlElement(required = true)
	protected String type;
	
	@XmlElement(required = true)
	protected Holder holder;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCvc() {
		return cvc;
	}

	public void setCvc(String cvc) {
		this.cvc = cvc;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Holder getHolder() {
		return holder;
	}

	public void setHolder(Holder holder) {
		this.holder = holder;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	@Override
	public String toString() {
		return "PaymentCard [number=" + number + ", cvc=" + cvc
				+ ", expirationDate=" + expirationDate + ", alias=" + alias
				+ ", type=" + type + ", holder=" + holder + "]";
	}
	
}
