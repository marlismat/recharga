package ve.com.digitel.clientwsfacadepayment.objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"number","cvv","expirationDate","type","holder"})
public class CreditCard {
	
	/**
	 * Número de la tarjeta 
	 */
	private String number;
    /**
     * Código de seguridad de la Tarjeta
     */
	private String cvv;
	/**
	 * Fecha de vencimiento de la tarjeta 
	 */
    private String expirationDate;
    /**
     * Tipo de tarjeta
     */
    private String type;
    /**
     * Objecto con los datos del propietario de la tarjeta 
     */
    private Holder holder;
    
	public CreditCard(String number, String cvv, String expirationDate, String type, Holder holder) {
		super();
		this.number = number;
		this.cvv = cvv;
		this.expirationDate = expirationDate;
		this.type = type;
		this.holder = holder;
	}

	public CreditCard() {
		super();
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
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

	@Override
	public String toString() {
		return "CreditCard [number=" + number + ", cvv=" + cvv
				+ ", expirationDate=" + expirationDate + ", type=" + type
				+ ", holder=" + holder + "]";
	}
    
	
}
