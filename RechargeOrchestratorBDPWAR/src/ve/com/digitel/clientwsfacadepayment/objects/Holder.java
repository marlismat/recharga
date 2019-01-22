package ve.com.digitel.clientwsfacadepayment.objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"name","id"})
public class Holder {
	
	/**
	 * Nombre del propietario de la tarjeta
	 */
	@XmlElement(required=true, nillable=false)
	private String name;
	
	/**
	 * Id del propietario
	 */
	@XmlElement(required=true, nillable=false)
	private String id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Holder(String name, String id) {
		super();
		this.name = name;
		this.id = id;
	}

	public Holder() {
		super();
	}

	@Override
	public String toString() {
		return "Holder [name=" + name + ", id=" + id + "]";
	}
	
	
	
	
}