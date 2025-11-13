package org.opental.core.model;

/**
 * Object holding a command
 */
public class Command {
	
	private String cmd;
	private String target;
	private String value;
	
	/**
	 * standard constructor
	 */
	public Command() {
	}
	
	/**
	 * construct a command
	 * @param aCommand command
	 * @param aTarget target
	 * @param aValue value
	 */
	public Command(String aCommand, String aTarget, String aValue) {
		this.cmd = aCommand;
		this.target = aTarget;
		this.value = aValue;
	}
	
    /**
     * get command name
     * @return command name
     */
	public String getCommand() {
		return cmd;
	}
	
	/**
	 * set command name
	 * @param command command name
	 */
	public void setCommand(String command) {
		this.cmd = command;
	}
	
	/**
	 * get target
	 * @return target
	 */
	public String getTarget() {
		return target;
	}
	
	/**
	 * set target
	 * @param target target
	 */
	public void setTarget(String target) {
		this.target = target;
	}
	
	/**
	 * get value
	 * @return value
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * set value
	 * @param value value
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "Command [command=" + this.cmd + ", target=" + this.target + ", value=" + this.value + "]";
	}
}
