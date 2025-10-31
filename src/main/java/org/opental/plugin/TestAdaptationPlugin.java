package org.opental.plugin;

import org.apache.commons.configuration2.CombinedConfiguration;

import javafx.scene.control.Tab;

public interface TestAdaptationPlugin {

	TestAdaptationType getType();
	
	String getName();
	
	Tab startup();
	
	CombinedConfiguration loadConfig();
	
	boolean shutdown();
	
	Class<?> getImplementation();
	
	String getAdaptionID();
}
