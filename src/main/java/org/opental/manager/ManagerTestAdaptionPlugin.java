package org.opental.manager;

import org.apache.commons.configuration2.CombinedConfiguration;
import org.opental.plugin.AbstractTestAdaptationPlugin;
import org.opental.plugin.TestAdaptationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ManagerTestAdaptionPlugin extends AbstractTestAdaptationPlugin {

	private static final Logger logger = LoggerFactory.getLogger(ManagerTestAdaptionPlugin.class);
	
	public static final String ID = "manager";
	
	public ManagerTestAdaptionPlugin() {
		super(TestAdaptationType.EXTENSION);
	}
	
	@Override
	public Class<?> getImplementation() {
		logger.debug("no implementation yet");
		return null;
	}
	
	@Override
	public String getAdaptionID() {
		return ID;
	}	
	
	@Override
	public String getName() {
		return "OpenTAL Manager Plugin";
	}

	/**
	 * load core config and merge with user config.
	 */
	@Override
	public CombinedConfiguration loadConfig() {
		return loadConfig(ID);
	}
}
