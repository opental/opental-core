package org.opental.core.exception;

/**
 * Specific exception -thrown when a specification does not validate against a 
 * given pattern, or some parts of a specification are misssing 
 */
public class InvalidSpecificationException extends RuntimeException {

	/**
	 * standard constructor
	 */
	public InvalidSpecificationException() {
		super();
	}
	
	/**
	 * construct an exception with describing message
	 * @param aMsg Message
	 */
	public InvalidSpecificationException(String aMsg) {
		super(aMsg);
	}
}
