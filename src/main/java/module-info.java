/**
 * 
 */
/**
 * 
 */
module opental.core {
	exports org.vebqa.vebtal.logging;
	exports org.vebqa.vebtal.model;
	exports org.vebqa.vebtal;
	exports org.vebqa.vebtal.manager;
	exports org.vebqa.vebtal.splash;
	exports org.vebqa.vebtal.sut;
	exports org.vebqa.vebtal.rest;
	exports org.vebqa.vebtal.gui;
	exports org.vebqa.vebtal.annotations;
	exports org.vebqa.vebtal.command;

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
	
	uses org.vebqa.vebtal.TestAdaptionPlugin;
	
}