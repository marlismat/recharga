package ve.com.digitel.rechargeorchestratorbdp.webserviceobject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerAccountInteractionRoleResponse", propOrder = {
    "interactionRole",
    "customerAccount"
})
public class CustomerAccountInteractionRoleResponse {
	
	@XmlElement( required = true)
	protected String interactionRole;
	
	@XmlElement(name = "CustomerAccount", required = true)
	protected CustomerAccountResponse customerAccount;

	public String getInteractionRole() {
		return interactionRole;
	}

	public void setInteractionRole(String interactionRole) {
		this.interactionRole = interactionRole;
	}

	public CustomerAccountResponse getCustomerAccount() {
		return customerAccount;
	}

	public void setCustomerAccount(CustomerAccountResponse customerAccount) {
		this.customerAccount = customerAccount;
	}
	
}
