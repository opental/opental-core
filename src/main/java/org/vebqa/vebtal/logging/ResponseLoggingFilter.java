package org.vebqa.vebtal.logging;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

@Logged
@Provider
public class ResponseLoggingFilter implements ContainerResponseFilter {

	private static final Logger logger = LoggerFactory.getLogger(ResponseLoggingFilter.class);
	
	public void filter(ContainerRequestContext req, ContainerResponseContext resp) throws IOException {
		logger.info("Response status: " + resp.getStatus());
	}
}
