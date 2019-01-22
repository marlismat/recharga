
package ve.com.digitel.clientwsfacadepayment.objects;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wsFacadePaymentRequest", propOrder = { "customerOrder" })
public class WSFacadePaymentRequest
  implements Serializable
{
  private static final long serialVersionUID = 1957380725171824751L;

  @XmlElement(required=true, nillable=false)
  private CustomerOrder customerOrder;

  public CustomerOrder getCustomerOrder()
  {
    return this.customerOrder;
  }

  public void setCustomerOrder(CustomerOrder customerOrder) {
    this.customerOrder = customerOrder;
  }

	@Override
	public String toString() {
		return "WSFacadePaymentRequest [customerOrder=" + customerOrder + "]";
	}
	  
  
}