package ve.com.digitel.clientwsfacadepayment.objects;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"code", "message", "causeCode", "ListEncripter", "causeMessage"})
public class FunctionalMessage
  implements Serializable
{
  private static final long serialVersionUID = 2606235329302772406L;

  @XmlElement(required=true, nillable=false)
  private String code;

  @XmlElement(required=true, nillable=false)
  private String message;

  @XmlElement(required=false, nillable=false)
  private String causeCode;
  
  @XmlElement(required=true, nillable=false)
  private List<String> ListEncripter;

  @XmlElement(required=false, nillable=false)
  private String causeMessage;

  public FunctionalMessage()
  {
  }

  public FunctionalMessage(String code, String message, String causeCode, String causeMessage)
  {
    this.code = code;
    this.message = message;
    this.causeCode = causeCode;
    this.causeMessage = causeMessage;
  }

  public String getCode()
  {
    return this.code;
  }

  public void setCode(String code)
  {
    this.code = code;
  }

  public String getMessage()
  {
    return this.message;
  }

  public void setMessage(String message)
  {
    this.message = message;
  }

  public String getCauseCode()
  {
    return this.causeCode;
  }

  public void setCauseCode(String causeCode)
  {
    this.causeCode = causeCode;
  }
  
  public List<String> getListEncripter() {
		return ListEncripter;
	}

	public void setListEncripter(List<String> listEncripter) {
		ListEncripter = listEncripter;
	}

  public String getCauseMessage()
  {
    return this.causeMessage;
  }

  public void setCauseMessage(String causeMessage)
  {
    this.causeMessage = causeMessage;
  }
}