package org.opental.core.model;

/**
 * This is a failed response
 */
public class FailedResponse extends Response {

	/**
	 * standard constructor
	 */
	public FailedResponse() {
		this.setCode(Response.FAILED);
	}

	/**
	 * construct a failed response with a qualified message
	 * @param aMsg Message
	 */
	public FailedResponse(String aMsg) {
		this.setCode(Response.FAILED);
		this.setMessage(aMsg);
	}
}
