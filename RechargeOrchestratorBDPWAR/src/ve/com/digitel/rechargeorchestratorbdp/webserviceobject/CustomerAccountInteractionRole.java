package ve.com.digitel.rechargeorchestratorbdp.webserviceobject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerAccountInteractionRole", propOrder = {
    "interactionRole",
    "customerAccount"
})
public class CustomerAccountInteractionRole {

	@XmlElement(required = true)
	protected String interactionRole;
	
	@XmlElement(required = true)
	protected CustomerAccount customerAccount;

	public String getInteractionRole() {
		return interactionRole;
	}

	public void setInteractionRole(String interactionRole) {
		this.interactionRole = interactionRole;
	}

	public CustomerAccount getCustomerAccount() {
		return customerAccount;
	}

	public void setCustomerAccount(CustomerAccount customerAccount) {
		this.customerAccount = customerAccount;
	}
}
