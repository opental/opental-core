package org.opental.core.model;

/**
 * Quick green response
 */
public class PassedResponse extends Response {

	/**
	 * construct a green response
	 */
	public PassedResponse() {
		this.setCode(Response.PASSED);
	}

	/**
	 * construct a green response with a message
	 * @param aMsg message
	 */
	public PassedResponse(String aMsg) {
		this.setCode(Response.PASSED);
		this.setMessage(aMsg);
	}
}
