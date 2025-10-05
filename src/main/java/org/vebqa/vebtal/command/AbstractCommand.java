package org.vebqa.vebtal.command;

import org.vebqa.vebtal.model.CommandType;

public abstract class AbstractCommand implements ICommand {
	
	protected final String command;
	protected final String target;
	protected final String value;
	
	protected CommandType type;
	
	protected AbstractCommand(String aCommand, String aTarget, String aValue) {
		this.command = aCommand.trim();
		this.target = aTarget.trim();
		this.value = aValue.trim();
	}
	
	public CommandType getType() {
		return this.type;
	}	
}
