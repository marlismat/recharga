package ve.com.digitel.clientwsfacadepayment.objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="ser:validateRule")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"EncripterServisResquest"})
public class ValidateRule {
	
	@XmlElement(name = "EncripterServisResquest", required=false, nillable=false)
	private EncripterServisResquest EncripterServisResquest;

	public EncripterServisResquest getEncripterServisRequest() {
		return EncripterServisResquest;
	}

	public void setEncripterServisRequest(
			EncripterServisResquest encripterServisResquest) {
		EncripterServisResquest = encripterServisResquest;
	}
	
}
