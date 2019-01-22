package ve.com.digitel.clientwsfacadepayment.objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"type","orderNumber","invoice","amount","traceid","idcomercio","terminal","mode","creditCard"})
public class Operation {	
	/**
	 * Tipo de operación a ejecutar.
	 */
	private String type;
	/**
	 * Numero de orden
	 */
    private String orderNumber;
    /**
     * Factura
     */
    private String invoice;
    /**
     * Monto de la transaccion 
     */
    private String amount;
    /**
     * Identificador
     */
    private String traceid;
    /**
     * Identificador de comercio
     */
    private String idcomercio;
    /**
     * Terminal
     */
    private String terminal;
    /**
     * 	Modo de la preautorización
     */
    private String mode;
    /**
     * Objeto con información de la tarjeta de crédito
     */
    private CreditCard creditCard;
	
    public Operation() {
		super();
	}
	
	public Operation(String type, String orderNumber, String invoice, String amount, String traceid, String idcomercio, String terminal, String mode, CreditCard creditCard) {
		super();
		this.type = type;
		this.orderNumber = orderNumber;
		this.invoice = invoice;
		this.amount = amount;
		this.traceid = traceid;
		this.idcomercio = idcomercio;
		this.terminal = terminal;
		this.mode = mode;
		this.creditCard = creditCard;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTraceid() {
		return traceid;
	}

	public void setTraceid(String traceid) {
		this.traceid = traceid;
	}

	public String getIdcomercio() {
		return idcomercio;
	}

	public void setIdcomercio(String idcomercio) {
		this.idcomercio = idcomercio;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@Override
	public String toString() {
		return "Operation [amount=" + amount + ", creditCard=" + creditCard
				+ ", idcomercio=" + idcomercio + ", invoice=" + invoice
				+ ", mode=" + mode + ", orderNumber=" + orderNumber
				+ ", terminal=" + terminal + ", traceid=" + traceid + ", type="
				+ type + "]";
	}
}
