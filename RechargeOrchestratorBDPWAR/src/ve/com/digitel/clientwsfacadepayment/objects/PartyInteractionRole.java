package ve.com.digitel.clientwsfacadepayment.objects;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"partyRole"})
public class PartyInteractionRole
  implements Serializable
{
  private static final long serialVersionUID = 2055659300438326566L;

  @XmlElement(required=true, nillable=false)
  private List<PartyRole> partyRole;

  public PartyInteractionRole()
  {
  }

  public PartyInteractionRole(List<PartyRole> partyRole)
  {
    this.partyRole = partyRole;
  }

  public List<PartyRole> getPartyRole()
  {
    return this.partyRole;
  }

  public void setPartyRole(List<PartyRole> partyRole)
  {
    this.partyRole = partyRole;
  }
}