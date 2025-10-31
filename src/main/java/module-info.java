/**
 * 
 */
/**
 * 
 */
module opental.core {
	exports org.opental.core.exception;
	exports org.opental.core.logging;
	exports org.opental.core.model;
	exports org.opental.core;
	exports org.opental.manager;
	exports org.opental.manager.splash;
	exports org.opental.core.sut;
	exports org.opental.server;
	exports org.opental.manager.gui;
	exports org.opental.core.annotations;
	exports org.opental.core.command;
	exports org.opental.plugin;

	requires com.fasterxml.jackson.core;
	requires com.fasterxml.jackson.databind;
	requires jakarta.json;
	requires transitive jakarta.ws.rs;
	requires transitive javafx.base;
	requires transitive javafx.controls;
	requires transitive javafx.graphics;
	requires transitive jetty.servlet.api;
	requires transitive org.apache.commons.configuration2;
	requires org.eclipse.jetty.server;
	requires org.eclipse.jetty.servlet;
	requires org.eclipse.jetty.util;
	requires org.glassfish.jersey.container.servlet;
	requires org.glassfish.jersey.core.common;
	requires org.glassfish.jersey.core.server;
	requires org.reflections;
	requires transitive org.slf4j;
	
	uses org.opental.plugin.TestAdaptationPlugin;
	
}