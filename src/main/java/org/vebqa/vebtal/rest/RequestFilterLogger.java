package org.vebqa.vebtal.rest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.glassfish.jersey.message.internal.ReaderWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;

public class RequestFilterLogger implements ContainerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(RequestFilterLogger.class);

	public RequestFilterLogger() {
		logger.info("init RequestFilterLogger");
	}

	@Override
	public void filter(ContainerRequestContext request) throws IOException {
		logger.info("Entering in Resource : /{} ", request.getUriInfo().getPath());
		logQueryParameters(request);
		
        //log entity stream...
        String entity = readEntityStream(request);
        if(null != entity && !entity.isEmpty()) {
            logger.info("Entity Stream : {}", entity);
        }

	}

	private void logQueryParameters(ContainerRequestContext requestContext) {
        Iterator<String> iterator = requestContext.getUriInfo().getPathParameters().keySet().iterator();
        while (iterator.hasNext()) {
            String name = iterator.next();
            List<String> obj = requestContext.getUriInfo().getPathParameters().get(name);
            String value = null;
            if(null != obj && !obj.isEmpty()) {
                value = obj.get(0);
            }
            logger.info("Query Parameter Name: {}, Value :{}", name, value);
        }
    }
	
    private String readEntityStream(ContainerRequestContext requestContext)
    {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        final InputStream inputStream = requestContext.getEntityStream();
        final StringBuilder builder = new StringBuilder();
        try
        {
            ReaderWriter.writeTo(inputStream, outStream);
            byte[] requestEntity = outStream.toByteArray();
            if (requestEntity.length == 0) {
                builder.append("");
            } else {
                builder.append(new String(requestEntity));
            }
            requestContext.setEntityStream(new ByteArrayInputStream(requestEntity) );
        } catch (IOException ex) {
            logger.info("----Exception occurred while reading entity stream :{}",ex.getMessage());
        }
        return builder.toString();
    }
}
