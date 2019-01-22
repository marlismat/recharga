
package ve.com.digitel.clientwsfacadepayment.objects;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wsFacadePaymentResponse", propOrder = {"functionalMessage", "errorMessage", "customerOrderItem"})
public class WSFacadePaymentResponse
  implements Serializable
{
  private static final long serialVersionUID = -5730074936953197046L;

  @XmlElement(required=false, nillable=false)
  private FunctionalMessage functionalMessage;

  @XmlElement(required=false, nillable=false)
  private ErrorMessage errorMessage;
  
  @XmlElement(required=false, nillable=false)
  private CustomerOrderItem customerOrderItem;

  public WSFacadePaymentResponse()
  {
  }

  public WSFacadePaymentResponse(FunctionalMessage functionalMessage, ErrorMessage errorMessage, CustomerOrderItem customerOrderItem)
  {
    this.functionalMessage = functionalMessage;
    this.errorMessage = errorMessage;
    this.customerOrderItem = customerOrderItem;
  }
  
  public void setCustomerOrderItem(CustomerOrderItem customerOrderItem) {
		this.customerOrderItem = customerOrderItem;
  }

  public CustomerOrderItem getCustomerOrderItem() {
		return customerOrderItem;
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

@Override
public String toString() {
	return "WSFacadePaymentResponse [functionalMessage=" + functionalMessage
			+ ", errorMessage=" + errorMessage + ", customerOrderItem="
			+ customerOrderItem + "]";
}
  
  
  
}