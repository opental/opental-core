package org.opental.core.model;

public class FailedResponse extends Response {

	public FailedResponse() {
		this.setCode(Response.FAILED);
	}

	public FailedResponse(String aMsg) {
		this.setCode(Response.FAILED);
		this.setMessage(aMsg);
	}
}
