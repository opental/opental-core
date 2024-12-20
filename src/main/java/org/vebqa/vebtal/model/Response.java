package org.vebqa.vebtal.model;

public class Response {

	public static final String PASSED = "0";
	
	public static final String FAILED = "1";
	
	/**
	 * data model
	 */

	/**
	 * a message, e.g sucess oder detailed failure
	 */
	private String message;
	
	/**
	 * a code, 0 for success, 1 for error
	 */
	private String code;
	
	/**
	 * in case of saving a value with a store* command, the key will be saved here
	 */
	private String storedKey;
	
	/**
	 * in case of saving a value with a store* command, the value will be save here
	 */
	private String storedValue;
	
	
	public String getStoredKey() {
		return storedKey;
	}
	public void setStoredKey(String storedKey) {
		this.storedKey = storedKey;
	}
	public String getStoredValue() {
		return storedValue;
	}
	public void setStoredValue(String storedValue) {
		this.storedValue = storedValue;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String toString() {
		if (this.storedKey == null || this.getStoredKey().isEmpty()) {
			return "[Code: " + this.code + ", " + "Message: " + this.message + "]";
		}
		return "[Code: " + this.code + ", " + "Message: " + this.message + ", " + "StoredKey: " + this.storedKey + ", " + "StoredValue: " + this.storedValue + "]";
	}
}
