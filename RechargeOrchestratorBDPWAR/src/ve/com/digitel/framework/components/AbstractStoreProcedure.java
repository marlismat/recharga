package ve.com.digitel.framework.components;

import org.springframework.jdbc.object.StoredProcedure;

import ve.com.digitel.rechargeorchestratorbdp.util.AppProperties;






import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public abstract class AbstractStoreProcedure extends StoredProcedure {

	
	public void setDataSourceJDNI(String dataSourceJDNI) throws NamingException {		
		DataSource dataSource = getDataSourceOfJDNI(dataSourceJDNI);
		setDataSource(dataSource);
	}

	private DataSource getDataSourceOfJDNI(String dataSourceJDNI) throws NamingException {

		InitialContext context = new InitialContext();
		DataSource dataSource = (DataSource)context.lookup(AppProperties.getProperty(dataSourceJDNI));

		return dataSource;
	}
	

}
