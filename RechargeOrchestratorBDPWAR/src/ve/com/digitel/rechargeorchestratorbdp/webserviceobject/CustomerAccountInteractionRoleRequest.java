package ve.com.digitel.rechargeorchestratorbdp.webserviceobject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerAccountInteractionRoleRequest", propOrder = {
    "interactionRole",
    "customerAccount"
})
public class CustomerAccountInteractionRoleRequest {
	
	@XmlElement( required = true)
	protected String interactionRole;
	
	@XmlElement(name = "CustomerAccount", required = true)
	protected CustomerAccountRequest customerAccount;

	public String getInteractionRole() {
		return interactionRole;
	}

	public void setInteractionRole(String interactionRole) {
		this.interactionRole = interactionRole;
	}

	public CustomerAccountRequest getCustomerAccount() {
		return customerAccount;
	}

	public void setCustomerAccount(CustomerAccountRequest customerAccount) {
		this.customerAccount = customerAccount;
	}
	
}
