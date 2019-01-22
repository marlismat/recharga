package ve.com.digitel.clientwsfacadepayment.objects;

import java.io.Serializable;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"functionalMessage", "errorMessage"})
public class EncripterServisResponse implements Serializable{
	
	  @XmlElement(name="functionalMessage",required=false, nillable=false)
	  private FunctionalMessage functionalMessage;

	  @XmlElement(name="errorMessage",required=false, nillable=false)
	  private ErrorMessage errorMessage;

	  public EncripterServisResponse()
	  {
	  }

	  public EncripterServisResponse(FunctionalMessage functionalMessage, ErrorMessage errorMessage)
	  {
	    this.functionalMessage = functionalMessage;
	    this.errorMessage = errorMessage;
	  }

	  public FunctionalMessage getFunctionalMessage()
	  {
	    return this.functionalMessage;
	  }

	  public void setFunctionalMessage(FunctionalMessage functionalMessage)
	  {
	    this.functionalMessage = functionalMessage;
	  }

	  public ErrorMessage getErrorMessage()
	  {
	    return this.errorMessage;
	  }

	  public void setErrorMessage(ErrorMessage errorMessage)
	  {
	    this.errorMessage = errorMessage;
	  }

}
