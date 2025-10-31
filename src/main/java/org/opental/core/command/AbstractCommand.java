package org.opental.core.command;

import org.opental.core.annotations.Keyword;
import org.opental.core.model.CommandType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractCommand implements ICommand {
	
	protected final String command;
	protected final String target;
	protected final String value;
	
	protected CommandType type;
	
	public static final Logger logger = LoggerFactory.getLogger(AbstractCommand.class);
	
	protected AbstractCommand(String aCommand, String aTarget, String aValue) {
		this.command = aCommand.trim();
		this.target = aTarget.trim();
		this.value = aValue.trim();
	}
	
	public CommandType getType() {
		return this.type;
	}
	
	/**
	 * Validate target and value input
	 * 
	 * @return
	 */
	public boolean validate() {
		Keyword anno = getClass().getAnnotation(Keyword.class);
		
		// check for empty spec
		if (!anno.hintTarget().isEmpty() && this.target.isEmpty()) return false;
		if (!anno.hintValue().isEmpty() && this.value.isEmpty()) return false;
		
		

		return true;
	}
}
