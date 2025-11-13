package org.opental.plugin;

import org.apache.commons.configuration2.CombinedConfiguration;

import javafx.scene.control.Tab;

/**
 * Interface to describe an adapter
 */
public interface TestAdaptationPlugin {

	TestAdaptationType getType();
	
	String getName();
	
	Tab startup();
	
	CombinedConfiguration loadConfig();
	
	boolean shutdown();
	
	Class<?> getImplementation();
	
	String getAdaptionID();
}
