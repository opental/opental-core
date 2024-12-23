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
	requires java.desktop;

	exports org.vebqa.vebtal.gui;
	opens org.vebqa.vebtal.model to javafx.base;
	opens images.gui;
	
	exports org.vebqa.vebtal;
	exports org.vebqa.vebtal.model;
	exports org.vebqa.vebtal.sut;
	exports org.vebqa.vebtal.command;
	exports org.vebqa.vebtal.annotations;
	
	uses org.vebqa.vebtal.TestAdaptionPlugin;
	
	provides org.vebqa.vebtal.TestAdaptionPlugin with
				org.vebqa.vebtal.manager.ManagerTestAdaptionPlugin;
}