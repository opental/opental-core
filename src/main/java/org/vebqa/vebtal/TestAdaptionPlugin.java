package org.vebqa.vebtal;

import org.apache.commons.configuration2.CombinedConfiguration;

import javafx.scene.control.Tab;

public interface TestAdaptionPlugin {

	TestAdaptionType getType();
	
	String getName();
	
	Tab startup();
	
	CombinedConfiguration loadConfig();
	
	boolean shutdown();
	
	Class<?> getImplementation();
	
	String getAdaptionID();
}
