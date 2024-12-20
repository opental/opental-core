module opental.core {
	
	requires org.slf4j;
	requires org.apache.commons.configuration2;
	requires org.reflections;
	requires jakarta.ws.rs;
	requires jakarta.json;
	requires jersey.common;
	requires jersey.server;
	requires jersey.container.servlet.core;
	requires jersey.media.moxy;
	requires com.fasterxml.jackson.core;
	requires com.fasterxml.jackson.databind;
	requires org.eclipse.jetty.server;
	requires org.eclipse.jetty.servlet;
	requires org.eclipse.persistence.moxy;
	requires javafx.graphics;
	requires javafx.controls;

	exports org.vebqa.vebtal;
	exports org.vebqa.vebtal.model;
}