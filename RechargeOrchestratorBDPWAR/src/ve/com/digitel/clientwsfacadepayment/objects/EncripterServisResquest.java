package ve.com.digitel.clientwsfacadepayment.objects;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder={"customerOrder"})
@XmlAccessorType(XmlAccessType.FIELD)
public class EncripterServisResquest implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6869669844485050087L;
	
	
	@XmlElement(name = "customerOrder", required=true, nillable=false)
	private EncripterUtils customerOrder;

	public EncripterUtils getCustomerOrder()
	{
	    return this.customerOrder;
	}

	public void setCustomerOrder(EncripterUtils customerOrder) {
	    this.customerOrder = customerOrder;
	}
}
