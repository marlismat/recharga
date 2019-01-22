package ve.com.digitel.rechargeorchestratorbdp.util;

import java.io.FileInputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import ve.com.digitel.bssint.EnvironmentUtil;
import ve.com.digitel.bssint.log.BSSIntLogger;



public class AppProperties implements Serializable{

/**
	 * 
	 */
	private static final long serialVersionUID = -4825800260401853252L;


private static Logger logger = BSSIntLogger.getBSSIntLogger(AppProperties.class);
		
	
	private static Properties messageProperties;
	
	private static Properties configProperties;
	
	private String configPropertiesName;
	
	private String messageListPropertiesName;
	
	
	
    public void init() throws Exception {
    	
      
         try {
        	 
         	Map<?, ?> mapServer = System.getenv();
         	
         	configPropertiesName = "properties_".concat((String)mapServer.get(EnvironmentUtil.getApplicationServer())).concat(".xml");
         	configProperties = new Properties();
            loadXMLFileProperties(configPropertiesName, configProperties);
            
            
            messageListPropertiesName = "messagelist_".concat((String)mapServer.get(EnvironmentUtil.getApplicationServer())).concat(".xml");
            messageProperties = new Properties();
            loadXMLFileProperties(messageListPropertiesName, messageProperties);
            
            logger.info("Las propiedades del servicio fueron cargadas con exito");
             
         } catch (Exception e) {
 			logger.error(e);
 			throw e;
         } 
         
         logger.info("Se realizo la carga del archivo de propiedades de manera exitosa");
     }
    
    
    
    public static String getProperty(String key) {
        return configProperties.getProperty(key);
    }
    
  
    public static String[] getMessageProperty(String key){
    	return messageProperties.getProperty(key).split(";");
    }
    
	
    
	public static void loadXMLFileProperties(String propertiesFileName, Properties properties) throws Exception{
		FileInputStream fis = new FileInputStream(EnvironmentUtil.getAbsoluteResourceName(propertiesFileName));
		properties.loadFromXML(fis);
		fis.close();
	}

}
