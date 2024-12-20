module opental.core {
	
	requires org.slf4j;
	requires org.apache.commons.configuration2;
	requires org.reflections;
	requires jakarta.ws.rs;
	requires jakarta.json;
	requires jersey.common;
	requires jersey.server;
	requires jersey.container.servlet.core;
	requires com.fasterxml.jackson.core;
	requires com.fasterxml.jackson.databind;
	requires org.eclipse.jetty.server;
	requires org.eclipse.jetty.servlet;
	requires javafx.graphics;
	requires javafx.controls;

	exports org.vebqa.vebtal.gui to javafx.graphics;
	opens org.vebqa.vebtal.model to javafx.base;
	
	uses org.vebqa.vebtal.TestAdaptionPlugin;
	
	provides org.vebqa.vebtal.TestAdaptionPlugin with
				org.vebqa.vebtal.manager.ManagerTestAdaptionPlugin;
}