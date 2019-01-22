package ve.com.digitel.rechargeorchestratorbdp.webserviceobject;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PartyInteractionRole", propOrder = {"partyRole"})
public class PartyInteractionRole {
	
	@XmlElement( required = true)
	protected List<PartyRole> partyRole;

	public List<PartyRole> getPartyRole() {
		return partyRole;
	}

	public void setPartyRole(List<PartyRole> partyRole) {
		this.partyRole = partyRole;
	}

	@Override
	public String toString() {
		return "PartyInteractionRole [partyRole=" + partyRole + "]";
	}
	
	

}