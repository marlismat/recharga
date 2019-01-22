package ve.com.digitel.clientwsfacadepayment.objects;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"code", "message", "trace"})
public class ErrorMessage
  implements Serializable
{
  private static final long serialVersionUID = 6192667075162587209L;

  @XmlElement(required=true, nillable=false)
  private String code;

  @XmlElement(required=true, nillable=false)
  private String message;

  @XmlElement(required=true, nillable=false)
  private String trace;

  public ErrorMessage()
  {
  }

  public ErrorMessage(String code, String message, String trace)
  {
    this.code = code;
    this.message = message;
    this.trace = trace;
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

  public String getTrace()
  {
    return this.trace;
  }

  public void setTrace(String trace)
  {
    this.trace = trace;
  }
}