package ve.com.digitel.rechargeorchestratorbdp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class XMLGregorianCalendarConverter implements Converter {
	
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(AppProperties.getProperty("FORMAT_DATE2"));

	
	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext marshallingContext) {
		
		XMLGregorianCalendar calendar = (XMLGregorianCalendar)value;
        Date date = calendar.toGregorianCalendar().getTime();
        writer.setValue(simpleDateFormat.format(date));
	}

	
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext unmarshallingContext) {

         try {
        	 GregorianCalendar calendar = new GregorianCalendar();
        	 calendar.setTimeZone(TimeZone.getTimeZone(AppProperties.getProperty("TIME_ZONE")));
             calendar.setTime(simpleDateFormat.parse(reader.getValue()));
             return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
                 
         } catch (ParseException e) {
                 throw new ConversionException(e.getMessage(), e);
         } catch (DatatypeConfigurationException e) {
        	 throw new ConversionException(e.getMessage(), e);
		}
	}

	public boolean canConvert(@SuppressWarnings("rawtypes") Class clazz) {
		return XMLGregorianCalendar.class.isAssignableFrom(clazz);
	}

}