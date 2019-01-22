package ve.com.digitel.clientwsfacadepayment.objects;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"name", "party"})
public class PartyRole
  implements Serializable
{
  private static final long serialVersionUID = 4437049833876730824L;

  @XmlElement(required=true, nillable=false)
  private String name;

  @XmlElement(required=true, nillable=false)
  private Party party;

  public PartyRole()
  {
  }

  public PartyRole(String name, Party party)
  {
    this.name = name;
    this.party = party;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public Party getParty()
  {
    return this.party;
  }

  public void setParty(Party party2)
  {
    this.party = party2;
  }
}