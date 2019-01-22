package ve.com.digitel.clientwsfacadepayment.objects;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "encripterUtils",propOrder={"ListEncripter"})
public class EncripterUtils implements Serializable
{

	private static final long serialVersionUID = -6899042841803019214L;
	
	@XmlElement(required=true, nillable=false)
	private List<String> ListEncripter;

	
	public List<String> getListEncripter() {
		return ListEncripter;
	}
		
	public void setListEncripter(List<String> listEncripter) {
		ListEncripter = listEncripter;
	}

}