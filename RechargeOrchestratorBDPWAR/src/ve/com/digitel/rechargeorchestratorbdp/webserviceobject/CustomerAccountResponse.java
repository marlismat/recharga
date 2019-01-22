package ve.com.digitel.rechargeorchestratorbdp.webserviceobject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerAccountResponse", propOrder = {
    "id",
    "functionalMessage"
})
public class CustomerAccountResponse {
	
	@XmlElement(required = true)
	protected String id;
	
	@XmlElement(name = "FunctionalMessage", required = false)
	protected FunctionalMessage functionalMessage;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public FunctionalMessage getFunctionalMessage() {
		return functionalMessage;
	}

	public void setFunctionalMessage(FunctionalMessage functionalMessage) {
		this.functionalMessage = functionalMessage;
	}
	
	
	

}
