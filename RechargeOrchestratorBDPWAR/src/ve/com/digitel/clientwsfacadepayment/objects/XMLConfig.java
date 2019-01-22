package ve.com.digitel.clientwsfacadepayment.objects;

public class XMLConfig {
	
	private String header;
	private String footer;
	private String headTemplate;
	private String securityTemplate;
	
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getFooter() {
		return footer;
	}
	public void setFooter(String footer) {
		this.footer = footer;
	}
	public String getHeadTemplate() {
		return headTemplate;
	}
	public void setHeadTemplate(String headTemplate) {
		this.headTemplate = headTemplate;
	}
	public String getSecurityTemplate() {
		return securityTemplate;
	}
	public void setSecurityTemplate(String securityTemplate) {
		this.securityTemplate = securityTemplate;
	}
	
	public XMLConfig XMLSOAPClient(String HeadParamts, String user, String password, String id) {
		XMLConfig xmlc = new XMLConfig();
		xmlc.setHeader("<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\"><S:Body><ns2:"+id+" xmlns:ns2=\"http://endpoint.soap.transacciones.msc_base.models/\">");
		xmlc.setFooter("</ns2:"+id+"></S:Body></S:Envelope>");
		String headTemplate = "<soapenv:Envelope "+HeadParamts+">"                   
                + "<soapenv:Header>";        
           String securityTemplate = "<usuario>"
                + user
                 + "</usuario>"
                + "<contrasena>"
                + password
                + "</contrasena>";
		xmlc.setHeadTemplate(headTemplate);
		xmlc.setSecurityTemplate(securityTemplate);
		return xmlc;
	}
	
	
	public XMLConfig XMLSOAPClientEncripta(String HeadParamts, String user, String password, String id) {
		XMLConfig xmlc = new XMLConfig();
	
		xmlc.setHeader("<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\"><S:Body><ns2:"+id+" xmlns:ns2=\"http://service.businessrulevalidatorservice.digitel.com.ve/\">");
		xmlc.setFooter("</ns2:"+id+"></S:Body></S:Envelope>");
		String headTemplate = "<soapenv:Envelope "+HeadParamts+">"                   
                + "<soapenv:Header>";        
           String securityTemplate = "<usuario>"
                + user
                 + "</usuario>"
                + "<contrasena>"
                + password
                + "</contrasena>";
		xmlc.setHeadTemplate(headTemplate);
		xmlc.setSecurityTemplate(securityTemplate);
		return xmlc;
	}
	
	public XMLConfig XMLSOAPClientWSFacadePayment(String HeadParamts, String user, String password, String id) {
		XMLConfig xmlc = new XMLConfig();
	
		xmlc.setHeader("<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\"><S:Body><ns2:"+id+" xmlns:ns2=\"http://service.businessrulevalidatorservice.digitel.com.ve/\">");
		xmlc.setFooter("</ns2:"+id+"></S:Body></S:Envelope>");
		String headTemplate = "<soapenv:Envelope "+HeadParamts+">"                   
                + "<soapenv:Header>";        
           String securityTemplate = "<usuario>"
                + user
                 + "</usuario>"
                + "<contrasena>"
                + password
                + "</contrasena>";
		xmlc.setHeadTemplate(headTemplate);
		xmlc.setSecurityTemplate(securityTemplate);
		return xmlc;
	}
}
