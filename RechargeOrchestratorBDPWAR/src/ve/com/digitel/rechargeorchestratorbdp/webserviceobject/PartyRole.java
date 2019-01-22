package ve.com.digitel.rechargeorchestratorbdp.webserviceobject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PartyRole", propOrder = {
    "name",
    "party"
})
public class PartyRole {

	@XmlElement( required = true)
	protected String name;
	
	
	@XmlElement( required = true)
	protected Party party;


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Party getParty() {
		return party;
	}


	public void setParty(Party party) {
		this.party = party;
	}

	@Override
	public String toString() {
		return "PartyRole [name=" + name + ", party=" + party + "]";
	}

}