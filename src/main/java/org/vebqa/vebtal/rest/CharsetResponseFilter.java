package org.vebqa.vebtal.rest;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.MediaType;

public class CharsetResponseFilter implements ContainerResponseFilter {

	private static final Logger logger = LoggerFactory.getLogger(CharsetResponseFilter.class);

	public CharsetResponseFilter() {
		logger.info("init CharsetResponseFilter");
	}

	@Override
	public void filter(ContainerRequestContext request, ContainerResponseContext response) throws IOException {
		logger.info("creating response for request path: {}", request.getUriInfo().getPath());
		MediaType contentType = response.getMediaType();
		request.getHeaders().clear();
		request.getHeaders().putSingle("Content-Type", contentType.toString() + ";charset=UTF-8");
		response.getHeaders().clear();
		response.getHeaders().putSingle("Content-Type", contentType.toString() + ";charset=UTF-8");
		logger.info("extend header with utf-8");
	}
}
