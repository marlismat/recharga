package ve.com.digitel.rechargeorchestratorbdp.webserviceobject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Holder", propOrder = {
    "name",
    "id",
    "address",
    "city",
    "zipCode",
    "state"
})
public class Holder {
	
	@XmlElement(required = true)
	protected String id;
	
	@XmlElement(required = true)
	protected String name ;
	
	@XmlElement(required = false)
	protected String address;

	@XmlElement(required = false)
	protected String city;
	
	@XmlElement(required = false)
	protected String zipCode;
	
	@XmlElement(required = false)
	protected String state;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	
}