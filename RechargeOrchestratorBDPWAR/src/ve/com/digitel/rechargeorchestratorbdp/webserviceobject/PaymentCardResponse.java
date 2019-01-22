package ve.com.digitel.rechargeorchestratorbdp.webserviceobject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentCardResponse", propOrder = {
    "alias"
})
public class PaymentCardResponse {
	@XmlElement(required = false)
	protected String alias;

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
}