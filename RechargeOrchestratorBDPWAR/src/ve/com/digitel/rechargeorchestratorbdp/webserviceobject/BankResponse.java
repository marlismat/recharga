package ve.com.digitel.rechargeorchestratorbdp.webserviceobject;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BankResponse", propOrder = {
		"success","message","id","code","reference","orderNumber",
		"voucher","sequence","approval"	})
public class BankResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5641568486209471027L;

	@XmlElement(required = true)
	private boolean success;
	
	@XmlElement(required = true)
	private String message;
	
	@XmlElement(required = true)
	private String id;
	
	@XmlElement(required = true)
	private String code;
	
	@XmlElement(required = true)
	private String reference;
	
	@XmlElement(required = true)
	private String orderNumber;
	
	@XmlElement(required = false)
	private String voucher;
	
	@XmlElement(required = false)
	private String sequence;
	
	@XmlElement(required = false)
	private String approval;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getVoucher() {
		return voucher;
	}

	public void setVoucher(String voucher) {
		this.voucher = voucher;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getApproval() {
		return approval;
	}

	public void setApproval(String approval) {
		this.approval = approval;
	}
	
	
	
}
