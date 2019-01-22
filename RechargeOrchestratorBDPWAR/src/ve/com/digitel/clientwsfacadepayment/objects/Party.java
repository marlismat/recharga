package ve.com.digitel.clientwsfacadepayment.objects;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"name"})
public class Party
  implements Serializable
{
  private static final long serialVersionUID = 6831073099901770112L;

  @XmlElement(required=true, nillable=false)
  private String name;

  public Party()
  {
  }

  public Party(String name)
  {
    this.name = name;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }
}