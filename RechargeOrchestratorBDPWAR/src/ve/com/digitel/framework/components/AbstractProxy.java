package ve.com.digitel.framework.components;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import ve.com.digitel.bssint.log.BSSIntLogger;
import ve.com.digitel.rechargeorchestratorbdp.util.AppProperties;



public abstract class AbstractProxy<REQUEST, RESPONSE>{

	protected Logger logger = BSSIntLogger.getBSSIntLogger(AbstractProxy.class);

	private String endPoint;

	private String namespaceURI;

	private String localPart;

	private String timeOut;

	private Integer attempts;

	private Long waitTime;
	
	


	private Throwable loadError;

	public AbstractProxy() {
		super();
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = AppProperties.getProperty(endPoint);
	}

	public String getNamespaceURI() {
		return namespaceURI;
	}

	public void setNamespaceURI(String namespaceURI) {
		this.namespaceURI = AppProperties.getProperty(namespaceURI);
	}

	public String getLocalPart() {
		return localPart;
	}

	public void setLocalPart(String localPart) {
		this.localPart = AppProperties.getProperty(localPart);
	}

	public String getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(String timeOut) {
		this.timeOut = AppProperties.getProperty(timeOut);
	}

	public Throwable getLoadError() {
		return loadError;
	}

	public void setLoadError(Throwable loadError) {
		this.loadError = loadError;
	}

	public Integer getAttempts() {
		return attempts;
	}

	public void setAttemptsCustom(String attempts) {
		this.attempts = Integer.valueOf(AppProperties.getProperty(attempts));
	}

	public Long getWaitTime() {
		return waitTime;
	}

	public void setWaitTimeCustom(String waitTime) {
		this.waitTime = Long.valueOf(AppProperties.getProperty(waitTime));
	}



	public void init() {

		try {
			logger.info("Inicializando contexto");
			this.makeInicializer();
			logger.info("Contexto Inicializado");
		} catch (Exception e) {
			setLoadError(e);
			logger.error("Error en inicializar contexto", e);
		}
	}

	public RESPONSE execute(REQUEST request) throws InterruptedException, RemoteException {

		int intentos = 0;
		do {
			try {
				return callService(request);
			} catch (Exception e) {
				Thread.sleep(waitTime);
				intentos++;
				logger.error("Intento " + intentos, e);
			}

		} while (intentos < attempts);

		throw new RemoteException("Error interno al inicializar contexto", loadError);
	}

	protected abstract void makeInicializer() throws Exception;

	protected abstract RESPONSE callService(REQUEST request) throws MalformedURLException;

}