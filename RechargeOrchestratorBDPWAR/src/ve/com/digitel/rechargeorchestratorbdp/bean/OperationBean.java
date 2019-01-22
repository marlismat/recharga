package ve.com.digitel.rechargeorchestratorbdp.bean;

import java.io.Serializable;

import ve.com.digitel.rechargeorchestratorbdp.webserviceobject.BankResponse;


public class OperationBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6983329487093870834L;

	/**
	 * 
	 */
	
	private boolean callCancellation;

	private String id;
	
	private String typeOperation;
	
	private String formOfPay;
	
	private String beneficiary;
	
	private String affiliate;
	
	private String type;
	
	private String orderNumber;
	
	private String paymentId;
	
	private String amount;
	
	private String ip;
	
	private String statusId;
	
	private String description;
	
	private String operationDateTime;
	
	private String purchaseOrderNumber;
		
	private String creditCardNumber;
	
	private String creditCardCvc;
	
	private String creditCardExpirationDate;
	
	private String creditCardType;
	
	private String creditCardHolderName;
	
	private String creditCardHolderId;
	
	private String creditCardHolderAddress;
	
	private String creditCardHolderCity;
	
	private String creditCardHolderZipCode;
	
	private String creditCardHolderState;
	
	private String creditCardAlias;
	
	private BankResponse bankResponse;
	
	private boolean valid;
	
	private String code;
	
	private String message;
	
	private Exception exception;
	
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

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOperationDateTime() {
		return operationDateTime;
	}

	public void setOperationDateTime(String operationDateTime) {
		this.operationDateTime = operationDateTime;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getCreditCardCvc() {
		return creditCardCvc;
	}

	public void setCreditCardCvc(String creditCardCvc) {
		this.creditCardCvc = creditCardCvc;
	}

	public String getCreditCardExpirationDate() {
		return creditCardExpirationDate;
	}

	public void setCreditCardExpirationDate(String creditCardExpirationDate) {
		this.creditCardExpirationDate = creditCardExpirationDate;
	}

	public String getCreditCardType() {
		return creditCardType;
	}

	public void setCreditCardType(String creditCardType) {
		this.creditCardType = creditCardType;
	}

	public String getCreditCardHolderName() {
		return creditCardHolderName;
	}

	public void setCreditCardHolderName(String creditCardHolderName) {
		this.creditCardHolderName = creditCardHolderName;
	}

	public String getCreditCardHolderId() {
		return creditCardHolderId;
	}

	public void setCreditCardHolderId(String creditCardHolderId) {
		this.creditCardHolderId = creditCardHolderId;
	}

	public String getCreditCardHolderAddress() {
		return creditCardHolderAddress;
	}

	public void setCreditCardHolderAddress(String creditCardHolderAddress) {
		this.creditCardHolderAddress = creditCardHolderAddress;
	}

	public String getCreditCardHolderCity() {
		return creditCardHolderCity;
	}

	public void setCreditCardHolderCity(String creditCardHolderCity) {
		this.creditCardHolderCity = creditCardHolderCity;
	}

	public String getCreditCardHolderZipCode() {
		return creditCardHolderZipCode;
	}

	public void setCreditCardHolderZipCode(String creditCardHolderZipCode) {
		this.creditCardHolderZipCode = creditCardHolderZipCode;
	}

	public String getCreditCardHolderState() {
		return creditCardHolderState;
	}

	public void setCreditCardHolderState(String creditCardHolderState) {
		this.creditCardHolderState = creditCardHolderState;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public BankResponse getBankResponse() {
		return bankResponse;
	}

	public void setBankResponse(BankResponse bankResponse) {
		this.bankResponse = bankResponse;
	}

	public String getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}

	public String getAffiliate() {
		return affiliate;
	}

	public void setAffiliate(String affiliate) {
		this.affiliate = affiliate;
	}

	

	public String getCreditCardAlias() {
		return creditCardAlias;
	}

	public void setCreditCardAlias(String creditCardAlias) {
		this.creditCardAlias = creditCardAlias;
	}

	public String getTypeOperation() {
		return typeOperation;
	}

	public void setTypeOperation(String typeOperation) {
		this.typeOperation = typeOperation;
	}

	public String getFormOfPay() {
		return formOfPay;
	}

	public void setFormOfPay(String formOfPay) {
		this.formOfPay = formOfPay;
	}

	public String getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}

	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}

	public boolean isCallCancellation() {
		return callCancellation;
	}

	public void setCallCancellation(boolean callCancellation) {
		this.callCancellation = callCancellation;
	}
}
