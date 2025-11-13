package org.opental.core;

/**
 * Class to hold keyword and its attributes
 * 
 */
public class KeywordEntry {

	private String module;
	private String command;
	private String hintTarget;
	private String hintValue;

	/**
	 * Spec of a single keyword
	 * 
	 * @param aModule references openTal adapter
	 * @param aCommand command
	 * @param aHintTarget description of target field
	 * @param aHintValue description of value field
	 */
	public KeywordEntry(String aModule, String aCommand, String aHintTarget, String aHintValue) {
		this.module = aModule;
		this.command = aCommand;
		this.hintTarget = aHintTarget;
		this.hintValue = aHintValue;
	}
	
	/**
	 * Get module name
	 * @return module name
	 */
	public String getModule() {
		return module;
	}

	/**
	 * Get command name
	 * @return command name
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * Get hint pattern for target
	 * @return hint pattern
	 */
	public String getHintTarget() {
		return hintTarget;
	}

	/**
	 * Get hint pattern for value
	 * @return hint pattern
	 */
	public String getHintValue() {
		return hintValue;
	}

}
