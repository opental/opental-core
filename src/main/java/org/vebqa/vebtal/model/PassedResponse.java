package org.vebqa.vebtal.model;

public class PassedResponse extends Response {

	public PassedResponse() {
		this.setCode(Response.PASSED);
	}

	public PassedResponse(String aMsg) {
		this.setCode(Response.PASSED);
		this.setMessage(aMsg);
	}
}
