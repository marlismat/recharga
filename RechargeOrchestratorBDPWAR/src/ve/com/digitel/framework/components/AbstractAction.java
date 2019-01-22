package ve.com.digitel.framework.components;

import java.util.Map;
import org.apache.log4j.Logger;
import ve.com.digitel.bssint.log.BSSIntLogger;
import ve.com.digitel.key.Key;

public abstract class AbstractAction extends AbstractComponent {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 3291744368502313328L;

	/* Define the next component to execute. */
	private AbstractComponent nextStep = null;

	/* Logger object. */
	protected Logger logger = BSSIntLogger.getBSSIntLogger (AbstractAction.class);


	/**
	 * 
	 *  <p>Constructor with parameters.
	 *  
	 *  @param name Action name.
	 * */
	public AbstractAction (String name) {

		// Call to super class.
		super (name);
	}

	/**
	 * 
	 *  <p>Method that execute the business logic.
	 *  
	 *  @param context Context rules business logic.
	 * */
	protected abstract void doExecute (Map<Key, Object> context);


	/**
	 * 
	 *  <p>Method that set the next step to execute.
	 *  
	 *  @param nextStep Next component to execute.
	 * */
	public void setNextStep (AbstractComponent nextStep) {

		// Set the next step.
		this.nextStep = nextStep;
	}

	/**
	 * 
	 *  <p>Method that return the next step to execute.
	 *  
	 *  @param nextStep Next component to execute.
	 * */
	public AbstractComponent getNextStep () {

		// Return the next step.
		return nextStep;
	}

	@Override
	public final void execute (Map<Key, Object> context) {

		this.doExecute (context);

		if (nextStep != null) {
			nextStep.execute (context);
		}
	}
	
}
