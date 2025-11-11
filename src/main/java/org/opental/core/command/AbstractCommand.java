package org.opental.core.command;

import org.opental.core.annotations.Keyword;
import org.opental.core.model.CommandStyle;
import org.opental.core.model.CommandType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractCommand implements ICommand {
	
	protected final String command;
	protected String target;
	protected String value;
	
	protected CommandType type;
	protected CommandStyle style;
	
	public static final Logger logger = LoggerFactory.getLogger(AbstractCommand.class);
	
	protected AbstractCommand(String aCommand, String aTarget, String aValue) {
		this.command = aCommand.trim();
		this.target = aTarget.trim();
		this.value = aValue.trim();
		this.style = CommandStyle.GENERIC;
	}
	
	protected AbstractCommand() {
		this.command = getClass().getAnnotation(Keyword.class).command();
		this.target = null;
		this.value = null;
		this.style = CommandStyle.FLUENT;
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
