package ve.com.digitel.clientwsfacadepayment.objects;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"id", "partyInteractionRole", "customerOrderItem" })
public class CustomerOrder implements Serializable {

	/**
	 * Id for serialization version.
	 */
	private static final long serialVersionUID = 2335118363928441105L;

	/**
	 * Identificador de la orden.
	 */
	@XmlElement(required = true, nillable = false)
	private String id;

	/**
	 * Objeto que contiene la información del canal.
	 */
	@XmlElement(required = true, nillable = false)
	private PartyInteractionRole partyInteractionRole;

	/**
	 * Objeto que agrupa los datos asociados a un producto.
	 */
	@XmlElement(required = true, nillable = false)	
	private CustomerOrderItem customerOrderItem;

	/**
	 * <p>Constructor por defecto.
	 */
	public CustomerOrder() {
		super();
	}

	/**
	 *  <p>Constructor con parametros.
	 * 
	 * @param id
	 * @param partyInteractionRole
	 * @param customerOrderItem
	 */
	public CustomerOrder(String id,
			PartyInteractionRole partyInteractionRole,
			CustomerOrderItem customerOrderItem
			) 
		{
		super();
		this.id = id;
		this.partyInteractionRole = partyInteractionRole;
		this.customerOrderItem = customerOrderItem;
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * @return the partyInteractionRole
	 */
	public PartyInteractionRole getPartyInteractionRole() {
		return partyInteractionRole;
	}

	/**
	 * @param partyInteractionRole the partyInteractionRole to set
	 */
	public void setPartyInteractionRole(PartyInteractionRole partyInteractionRole) {
		this.partyInteractionRole = partyInteractionRole;
	}

	/**
	 * @return the partyInteractionRole
	 */
	public CustomerOrderItem getCustomerOrderItem() {
		return customerOrderItem;
	}

	/**
	 * @param customerOrderItem the customerOrderItem to set
	 */	
	public void setCustomerOrderItem(CustomerOrderItem customerOrderItem) {
		this.customerOrderItem = customerOrderItem;
	}

	@Override
	public String toString() {
		return "CustomerOrder [customerOrderItem=" + customerOrderItem
				+ ", id=" + id + ", partyInteractionRole="
				+ partyInteractionRole + "]";
	}
}