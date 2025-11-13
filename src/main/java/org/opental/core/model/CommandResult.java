package org.opental.core.model;

import javafx.beans.property.SimpleStringProperty;

/**
 * Wrapper object for a result to display in the gui
 */
public class CommandResult {
	/**
	 * command
	 */
	private final SimpleStringProperty command;
	/**
	 * target
	 */
	private final SimpleStringProperty target;
	/**
	 * value
	 */
	private final SimpleStringProperty value;
	/**
	 * result
	 */
	private final SimpleStringProperty result;
	/**
	 * loginfo
	 */
	private final SimpleStringProperty loginfo;
    /**
     * type
     */
	private final CommandType type;
	
	/**
	 * construct a CommandResult
	 * @param aCommand
	 * @param aTarget
	 * @param aValue
	 * @param aType
	 */
	public CommandResult(String aCommand, String aTarget, String aValue, CommandType aType) {
		this.command = new SimpleStringProperty(aCommand);
		this.target = new SimpleStringProperty(aTarget);
		this.value = new SimpleStringProperty(aValue);
		this.result = new SimpleStringProperty("testing..");
		this.loginfo = new SimpleStringProperty("");
		this.type = aType;
	}

	/**
	 * get command name
	 * @return command name
	 */
	public String getCommand() {
		return command.get();
	}

	/**
	 * get target info
	 * @return target
	 */
	public String getTarget() {
		return target.get();
	}

	/**
	 * get value info
	 * @return value
	 */
	public String getValue() {
		return value.get();
	}

	/**
	 * get result info
	 * @return result info
	 */
	public String getResult() {
		return result.get();
	}
	
	/**
	 * set the result
	 * @param bResult result
	 */
	public void setResult(boolean bResult) {
		if (!bResult) {
			this.result.set("FAILED");
		} else {
			this.result.set("PASSED");
		}
	}

	/**
	 * get logging info
	 * @return log info
	 */
	public String getLoginfo() {
		return this.loginfo.get();
	}
	
	/**
	 * set the log info
	 * @param anInfo info
	 */
	public void setLogInfo(String anInfo) {
		this.loginfo.set(anInfo);
	}
	
	/**
	 * get the command type
	 * @return CommandType
	 */
	public CommandType getType() {
		return this.type;
	}
}
