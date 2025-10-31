package org.opental.manager.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import org.apache.commons.configuration2.CombinedConfiguration;
import org.opental.core.GuiManager;
import org.opental.core.KeywordFinder;
import org.opental.plugin.TestAdaptationPlugin;
import org.opental.plugin.TestAdaptationType;
import org.opental.server.RestServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class RoboManager extends Application {

	public static final Logger logger = LoggerFactory.getLogger(RoboManager.class);

	public static final RestServer singleServer = new RestServer();

	public RoboManager() {
		// standard constructor
	}

	public static void main(String[] args) {
		Application.launch(RoboManager.class, args);
	}

	@Override
	public void init() throws Exception {

		GuiManager.getinstance().getMainTab().setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

		// Create config tab
		GuiManager.getinstance().getMainTab().getTabs().add(GuiManager.getinstance().createConfigTab());
		GuiManager.getinstance().getMainTab().getTabs().add(GuiManager.getinstance().createLogTab());

		GuiManager.getinstance().getMain().setCenter(GuiManager.getinstance().getMainTab());

		// load plugin configurations
		Iterator<TestAdaptationPlugin> plugins = ServiceLoader.load(TestAdaptationPlugin.class).iterator();
		if (!plugins.hasNext()) {
			logger.info("No plugins found!");
			GuiManager.getinstance().writeLog("No plugins found!");
		}

		// save all keyword packages here
		List<String> tCommandPackages = new ArrayList<>();
		
		while (plugins.hasNext()) {
			TestAdaptationPlugin robo = plugins.next();
			logger.info("Found next plugin: {}", robo.getAdaptionID());
			if (robo.getType() == TestAdaptationType.ADAPTER || robo.getType() == TestAdaptationType.EXTENSION) {
				CombinedConfiguration tCfgAdapter = new CombinedConfiguration();
				tCfgAdapter.addProperty("adapter." + robo.getAdaptionID() + ".root", robo.getClass().getPackage().getName());
				GuiManager.getinstance().getConfig().addConfiguration(tCfgAdapter);
				
				try {
					CombinedConfiguration tConfig = robo.loadConfig();
					if (tConfig != null) {
						GuiManager.getinstance().getConfig().addConfiguration(tConfig);
					}
				} catch (Exception e) {
					logger.error("Error while loading config from plugin: {} because of {}", robo.getName(),
							e.getMessage(), e);
				}
			}
			Thread.sleep(50);
		}

		// store all possible command packages
		GuiManager.getinstance().getConfig().addProperty("cmdPackages", tCommandPackages);
		
		// show actual config
		GuiManager.getinstance().showConfig();

		// start tabs for gui system
		plugins = ServiceLoader.load(TestAdaptationPlugin.class).iterator();
		if (!plugins.hasNext()) {
			GuiManager.getinstance().writeLog("No plugins found!");
		}

		// After loading plugins, scan classpath for custom keywords. Keyword storage is
		// needed in plugin-startup methods
		KeywordFinder.getinstance().scan();

		while (plugins.hasNext()) {
			TestAdaptationPlugin robo = plugins.next();
			
			if (robo.getType() == TestAdaptationType.ADAPTER) {
				try {
					logger.info("Start plugin ({}) of type ({}) ", robo.getName(), robo.getType());
					GuiManager.getinstance().getMainTab().getTabs().add(robo.startup());
				} catch (Exception e) {
					logger.error("Error while starting plugin: {}", robo.getName(), e);
				}
			}
			Thread.sleep(250);
		}

		Parameters parameters = getParameters();
		Map<String, String> namedArguments = parameters.getNamed();

		// we can process the following keys:
		// --port
		int port = 0; // default, not used
		for (Map.Entry<String, String> entry : namedArguments.entrySet()) {
			logger.info("found cli key ({}) ", entry.getKey());
			switch (entry.getKey()) {
			case "port":
				port = Integer.parseInt(entry.getValue());
				break;
			default : logger.info("option not processed: {}", entry.getKey());
			}
		}
		
		if (port == 0) {
			port = GuiManager.getinstance().getConfig().getInt("server.port", 0);
		}

		final int usePort = port;
		
		if (port > 0) {
		
		// Start REST Server
		Thread t = new Thread(new Runnable() {

			public void run() {
				singleServer.setPort(usePort);
				singleServer.startServer();
			}
		});

		t.start();

		while (!t.isAlive()) {
			Thread.sleep(50);
		}

		GuiManager.getinstance().writeLog("Server listening on port: " + usePort);
		} else {
			GuiManager.getinstance().writeLog("No REST server started!");
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle("Test Adaption Manager");

		double width = GuiManager.getinstance().getConfig().getDouble("manager.width");
		double height = GuiManager.getinstance().getConfig().getDouble("manager.height");
		primaryStage.setScene(new Scene(GuiManager.getinstance().getMain(), width, height));

		primaryStage.setOnCloseRequest(evt -> shutdown(primaryStage));

		primaryStage.show();
	}

	/**
	 * Shutdown application - call all plugins and tear down.
	 * 
	 * @param mainWindow
	 */
	private void shutdown(Stage mainWindow) {
		Iterator<TestAdaptationPlugin> plugins = ServiceLoader.load(TestAdaptationPlugin.class).iterator();
		while (plugins.hasNext()) {
			TestAdaptationPlugin robo = plugins.next();
			GuiManager.getinstance().writeLog("Shutdown plugin " + robo.getName());
			try {
				robo.shutdown();
			} catch (Exception e) {
				logger.error("Error while starting plugin: " + robo.getName(), e);
			}
		}
		RoboManager.singleServer.shutdownServer();
		try {
			stop();
		} catch (Exception e) {
			logger.error("Error while stopping application.", e);
		}

		mainWindow.close();
	}

	public static void addTab(Tab aTab) {
		GuiManager.getinstance().getMainTab().getTabs().add(aTab);
	}
}
