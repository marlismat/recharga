package ve.com.digitel.rechargeorchestratorbdp.util;


import org.apache.log4j.Logger;

import ve.com.digitel.bssint.log.BSSIntLogger;

import com.thoughtworks.xstream.XStream;

public final class XStreamFactory {
	
	static private Logger logger = BSSIntLogger.getBSSIntLogger (XStreamFactory.class);
	public static XStream xstream;
	
	private XStreamFactory() {}
	
	static public XStream makeXStream() {
		
		
		if(xstream == null){
			
			logger.info("Obteniendo instancia XStream");	
			xstream = new XStream();
			xstream.registerConverter(new CalendarConverter());
			xstream.registerConverter(new XMLGregorianCalendarConverter());
			
		}
		
		
		return xstream;
	}
	


}
