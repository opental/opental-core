package org.vebqa.vebtal.manager;

import org.apache.commons.configuration2.CombinedConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vebqa.vebtal.AbstractTestAdaptionPlugin;
import org.vebqa.vebtal.TestAdaptionType;

public class ManagerTestAdaptionPlugin extends AbstractTestAdaptionPlugin {

	private static final Logger logger = LoggerFactory.getLogger(ManagerTestAdaptionPlugin.class);
	
	public static final String ID = "manager";
	
	public ManagerTestAdaptionPlugin() {
		super(TestAdaptionType.EXTENSION);
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
