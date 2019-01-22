package ve.com.digitel.framework.components;

import java.util.Map;

import org.apache.log4j.Logger;

import ve.com.digitel.bssint.log.BSSIntLogger;
import ve.com.digitel.key.Key;

public abstract class AbstractExceptionRule extends AbstractComponent {



	/**
	 * 
	 */
	private static final long serialVersionUID = -8857004933515131986L;

	protected Logger logger = BSSIntLogger.getBSSIntLogger (AbstractExceptionRule.class);
	
	private AbstractComponent positiveOutcomeStep = null;

	private AbstractComponent negativeOutcomeStep = null;
	
	private AbstractComponent exceptionOutcomeStep = null;


	public AbstractExceptionRule (String name) {
		super (name);
	}

	protected abstract boolean makeDecision (Map<Key, Object> context) throws RuleException;


	public void setPositiveOutcomeStep (AbstractComponent positiveOutcomeStep) {
		this.positiveOutcomeStep = positiveOutcomeStep;
	}


	public AbstractComponent getPositiveOutcomeStep () {
		return positiveOutcomeStep;
	}


	public void setNegativeOutcomeStep (AbstractComponent negativeOutcomeStep) {
		this.negativeOutcomeStep = negativeOutcomeStep;
	}


	public AbstractComponent getNegativeOutcomeStep () {
		return negativeOutcomeStep;
	}

	
	public void setExceptionOutcomeStep(AbstractComponent exceptionOutcomeStep) {
		this.exceptionOutcomeStep = exceptionOutcomeStep;
	}
	
	
	public AbstractComponent getExceptionOutcomeStep() {
		return exceptionOutcomeStep;
	}
		
	@Override
	public final void execute(Map<Key, Object> context) {

		try {
			
			boolean outcome = makeDecision(context);

			if (outcome) {
				positiveOutcomeStep.execute(context);
			} else {
				negativeOutcomeStep.execute(context);
			}
			
		} catch (RuleException e) {
			exceptionOutcomeStep.execute(context);
		}

	}


}
