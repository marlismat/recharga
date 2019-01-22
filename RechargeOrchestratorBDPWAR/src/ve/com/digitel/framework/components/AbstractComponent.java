package ve.com.digitel.framework.components;

import java.io.Serializable;
import java.util.Map;
import ve.com.digitel.key.Key;

public abstract class AbstractComponent implements Serializable {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4578548510856001884L;
	/* Component name. */
	protected String name = "";

	/**
	 * 
	 * <p>
	 * Unique constructor with parameters.
	 * 
	 * @param name
	 *            The component name.
	 */
	public AbstractComponent(String name) {

		// Call to super class.
		super();

		// Set the internal values.
		this.name = name == null ? this.getClass().toString() : name.trim();
	}

	/***************************************************************************
	 * 
	 * <p>
	 * Method that execute the rules logic.
	 * 
	 * @param context
	 *            Context for execute the business logic.
	 */
	public abstract void execute(Map<Key, Object> args);

}
