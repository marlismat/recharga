package ve.com.digitel.clientwsfacadepayment.connection;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.SSLSocketFactory;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import org.apache.http.Consts;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

import ve.com.digitel.bssint.log.BSSIntLogger;
import ve.com.digitel.clientwsfacadepayment.objects.XMLConfig;

@SuppressWarnings({ "unused", "deprecation","unchecked" })
public class JaxbApiGold {
	
	private static Object object;
	private static String msisdn;

	public JaxbApiGold() {
		// TODO Auto-generated constructor stub
	}
	
	public static final Logger log = BSSIntLogger.getBSSIntLogger(JaxbApiGold.class);
	

	public static Object execute(Boolean security, String user, String password, String endPoint, String namespaceURI, Object request, String requestName, Class requestClass, Class responseClass, String soapAction , String HeadParamts , Object obj, String Token, String type) throws Exception {
			
	    try {
	    
            JAXBContext jaxbContextRequest = JAXBContext.newInstance(requestClass);
            Marshaller jaxbMarshaller = jaxbContextRequest.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            JAXBElement jx = new JAXBElement(new QName(requestName), requestClass, request);
            StringWriter writer = new StringWriter();
            jaxbMarshaller.marshal(jx, writer);
            CloseableHttpClient httpClient = HttpClients.custom()
            .setHostnameVerifier(new AllowAllHostnameVerifier())
            .setSslcontext(
                       new SSLContextBuilder().loadTrustMaterial(null,
                                   new TrustStrategy() {
                             public boolean isTrusted(
                                         X509Certificate[] arg0,
                                         String arg1)
                             throws CertificateException {
                                   return true;
                             }
                       }).build()).build();

            HttpPost httpPost = new HttpPost(endPoint);
            httpPost.addHeader("SOAPAction", "");
            XMLConfig xmlcf = new XMLConfig();
            
            if(type != "" && type != null){
            	if(type.equals("wsfacadepayment")){
        			xmlcf = xmlcf.XMLSOAPClientWSFacadePayment(HeadParamts, user, password, "executeWSFacadePaymentResponse");
        		}
            }else{
            	return null;
            }
    	
            String bodyTemplate = "</soapenv:Header><soapenv:Body><ser:executeWSFacadePayment><wsFacadePaymentRequest>";
            String footTemplate = "</wsFacadePaymentRequest></ser:executeWSFacadePayment></soapenv:Body></soapenv:Envelope>";
            String requestString = null;
            
            /* 
             * El proyecto no posee WSDL por lo tanto no reconoce el tag <wsFacadePaymentRequest> del objeto REQUEST,
             * por este motivo se tiene que parsear en esta clase, se elimina el <ser:executeWSFacadePayment>
             * y posteriormente se colocará de nuevo en el XML, pero de la manera correcta, primero <wsFacadePaymentRequest>
             * y luego  <ser:executeWSFacadePayment>
             * */
            
            String str_aux = writer.toString().replaceAll("<ser:executeWSFacadePayment>", "").replaceAll("</ser:executeWSFacadePayment>", "");
      
            if (security) {
            	requestString = xmlcf.getHeadTemplate() + xmlcf.getSecurityTemplate() + bodyTemplate + str_aux + footTemplate;
                //System.out.println("requestString "+ requestString);
            } else {
                 requestString = xmlcf.getHeadTemplate() + bodyTemplate + str_aux + footTemplate;
                 //System.out.println("requestString2 "+ requestString);
            }
            //System.out.println(requestString);
            StringEntity entity = new StringEntity(requestString);
            
            if (security) {
            	 entity.setContentType("text/xml");
                 httpPost.setHeader("Content-Type", "text/xml");
            }
            
            
           httpPost.setEntity(entity);
           CloseableHttpResponse response = null;
           //System.out.println("Enviando XML: ");
           
           //Ejecuta
            response = httpClient.execute(httpPost);
            //System.out.println(response);
            InputStream in = response.getEntity().getContent();
            InputStreamReader is = new InputStreamReader(in);
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(is);
            String read = br.readLine();

            while (read != null) {
                 sb.append(read);
                 read = br.readLine();
            }
            
            //System.out.println("Recibiendo XML: " + sb.toString());
            //System.out.println("Recibiendo XML: ");
            
            if (sb.toString().contains("<?xml version='1.0' encoding='ISO-8859-1'?><soapenv:Fault") || (sb.toString().contains("<soap:Fault"))) {
            	
	           	 String soapHeader = "";
	           	 String soapFooter = "";
	    	 
	           	if (sb.toString().contains("<?xml version='1.0' encoding='ISO-8859-1'?>")){
	           		soapHeader = "<?xml version='1.0' encoding='ISO-8859-1'?><soapenv:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"  xmlns:vouc=\"http://oslee.orga.com/bsg/ra/ws/digitel/service/voucher\"><soapenv:Body>";
	                   soapFooter = "</soapenv:Body></soapenv:Envelope>";    
	           	}else{
	           	  soapHeader = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"  xmlns:vouc=\"http://oslee.orga.com/bsg/ra/ws/digitel/service/voucher\"><soap:Body>";
	                 soapFooter = "</soap:Body></soap:Envelope>";                  
	           	}
                 String messageSoapFault = sb.toString().replace(soapHeader, "").replace(soapFooter, "");
                 StringReader reader = new StringReader(messageSoapFault);
                 String errorCode = null;
                 Pattern p = Pattern.compile("<ErrorCode>(.*)</ErrorCode>");
                 Matcher m = p.matcher(messageSoapFault);
                 
                 if (m.find()) {
                       errorCode = m.group(1).replaceFirst("<ErrorCode>(.*)</ErrorCode>", "$1");
                 }
                 String errorDescription = null;
                 p = Pattern.compile("<ErrorDescription>(.*)</ErrorDescription>");
                 m = p.matcher(messageSoapFault);

                 if (m.find()) {
                       errorDescription = m.group(1).replaceFirst("<ErrorDescription>(.*)</ErrorDescription>", "$1");
                 }
                                
                 log.error(" Error  Type:"+errorCode +"  Error des:"+errorDescription);
                 
                 throw new Exception (errorDescription);  
                 
               /*  String sResp;
                 sResp = Utils.msjError(errorDescription);
                 if (sResp.equalsIgnoreCase("0"))
                 {
                	 log.error("El Servicio Gold ("+requestName+") ha devuelto un SOAP Fault Erro Type:"+ errorCode +" Error des:" +errorDescription);
                	 throw new Exception ("El Servicio Gold ("+requestName+") ha devuelto un SOAP Fault :"+errorDescription);              
                 }else{ 
                	 if (sResp.contains("@gsm")){
                		System.out.println("Entro en la validacion:"+getMsisdn());
                		sResp = sResp.replace("@gsm",  getMsisdn());
                	 }else{
                		 System.out.println("no entro");
                		 sResp.replace("@gsm", getMsisdn());
                	 }
                	 System.out.println("Servicio"+requestName);
                	 log.error(sResp);
                   	 throw new Exception (sResp);      
                 }*/
                
            } else {                
            	 String soapHeader = "";
                 String soapFooter = "";
                 if (sb.toString().contains("<?xml version='1.0' encoding='UTF-8'?>")){
            			soapHeader = xmlcf.getHeader();
                        soapFooter = xmlcf.getFooter();
                }else{               		
                	  soapHeader = xmlcf.getHeader();
            	      soapFooter = xmlcf.getFooter();
               	}
                 
                 String responseString = sb.toString().replace(soapHeader, "").replace(soapFooter, "");
                 StringReader reader = new StringReader(responseString);
                 JAXBContext jaxbContextResponse = JAXBContext.newInstance(responseClass);
                 Unmarshaller unmarshaller = jaxbContextResponse.createUnmarshaller();
                 JAXBElement root = unmarshaller.unmarshal(new StreamSource(reader), responseClass);
                 obj = (Object) root.getValue();
                 //System.out.println(obj);
                 return obj;
            }

      }catch (HttpHostConnectException e) {
    	  log.error(e.getMessage());
    	 
    	  new Exception(e.getCause());
      }catch (Exception e) {
  
            log.error(e.getMessage());
            throw e;
      }	
    	   
      return soapAction;

	}

	public static Object getObj() {
		return object;
	}

	public static void setObj(Object obj) {
		object = obj;
	}

	public static String getMsisdn() {
		return msisdn;
	}

	public static void setMsisdn(String msisdn) {
		JaxbApiGold.msisdn = msisdn;
	}
	
}
