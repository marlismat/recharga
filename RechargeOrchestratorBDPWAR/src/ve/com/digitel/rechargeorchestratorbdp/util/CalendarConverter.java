package ve.com.digitel.rechargeorchestratorbdp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class CalendarConverter implements Converter {
	
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(AppProperties.getProperty("FORMAT_DATE2"));

	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext marshallingContext) {
		
        Calendar calendar = (Calendar)value;
        calendar.setTimeZone(TimeZone.getTimeZone(AppProperties.getProperty("TIME_ZONE")));
        Date date = calendar.getTime();
        writer.setValue(simpleDateFormat.format(date));
	}

	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext unmarshallingContext) {
		
		 GregorianCalendar calendar = new GregorianCalendar();
		 calendar.setTimeZone(TimeZone.getTimeZone(AppProperties.getProperty("TIME_ZONE")));

         try {
                 calendar.setTime(simpleDateFormat.parse(reader.getValue()));
         } catch (ParseException e) {
                 throw new ConversionException(e.getMessage(), e);
         }
         
         return calendar;

	}

	public boolean canConvert(@SuppressWarnings("rawtypes") Class clazz) {
		return Calendar.class.isAssignableFrom(clazz);
	}

}
