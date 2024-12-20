package org.vebqa.vebtal.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

import org.apache.commons.configuration2.CombinedConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vebqa.vebtal.GuiManager;
import org.vebqa.vebtal.KeywordFinder;
import org.vebqa.vebtal.TestAdaptionPlugin;
import org.vebqa.vebtal.TestAdaptionType;
import org.vebqa.vebtal.rest.RestServer;

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
		// LauncherImpl.launchApplication(RoboManager.class, AppPreloader.class, args);
	}

	@Override
	public void init() throws Exception {

//		ConfigurationBuilder<BuiltConfiguration> configBuilder = ConfigurationBuilderFactory.newConfigurationBuilder();
//		
//		configBuilder.setStatusLevel(Level.INFO);
//		configBuilder.setConfigurationName("VEBTALRT");
//
//		// create the appender
//		LayoutComponentBuilder layoutBuilder = configBuilder.newLayout("SerializedLayout");
//		
//		AppenderComponentBuilder appenderBuilder = configBuilder.newAppender("remoteAppender", "Socket").addAttribute("host", "localhost").addAttribute("port", 85).add(layoutBuilder);
//		configBuilder.add(appenderBuilder);
//		
//		// create a new logger
//		configBuilder.add(configBuilder.newLogger("rtlogger", Level.DEBUG).add(configBuilder.newAppenderRef("remoteAppender")).addAttribute("additivity", false));
//		
//		configBuilder.add(configBuilder.newRootLogger(Level.DEBUG).add(configBuilder.newAppenderRef("remoteAppender")));
//		
//		LoggerContext ctx = Configurator.initialize(configBuilder.build());    	

		// BorderPane zur Aufnahme der Tabs
		GuiManager.getinstance().getMainTab().setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

		// Create config tab
		GuiManager.getinstance().getMainTab().getTabs().add(GuiManager.getinstance().createConfigTab());

		GuiManager.getinstance().getMain().setCenter(GuiManager.getinstance().getMainTab());

		// Log Area
		/** Logs **/
		GuiManager.getinstance().getMain().setBottom(GuiManager.getinstance().getLogArea());

		// load plugin configurations
		Iterator<TestAdaptionPlugin> plugins = ServiceLoader.load(TestAdaptionPlugin.class).iterator();
		if (!plugins.hasNext()) {
			logger.info("No plugins found!");
			GuiManager.getinstance().writeLog("No plugins found!");
		}

		// save all keyword packages here
		ArrayList<String> tCommandPackages = new ArrayList<String>();
		
		while (plugins.hasNext()) {
			TestAdaptionPlugin robo = plugins.next();
			logger.info("Found next plugin: {}", robo.getAdaptionID());
//			LauncherImpl.notifyPreloader(this,
//					new AppPreloader.ActualTaskNotification("Load configuration for plugin: " + robo.getName()));
			// we will load configs from Adapter and extensions
			if (robo.getType() == TestAdaptionType.ADAPTER || robo.getType() == TestAdaptionType.EXTENSION) {
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
		plugins = ServiceLoader.load(TestAdaptionPlugin.class).iterator();
		if (!plugins.hasNext()) {
			GuiManager.getinstance().writeLog("No plugins found!");
		}

		// After loading plugins, scan classpath for custom keywords. Keyword storage is
		// needed in plugin-startup methods
		KeywordFinder.getinstance().scan();

		while (plugins.hasNext()) {
			TestAdaptionPlugin robo = plugins.next();
			
//			  LauncherImpl.notifyPreloader(this, new AppPreloader.ActualTaskNotification(
//			  "Start plugin of type (" + robo.getType() + "): " + robo.getName()));
			 
			// we will start adapter only at this point
			if (robo.getType() == TestAdaptionType.ADAPTER) {
				try {
					// logger.info("Start plugin of type (" + robo.getType() + "): " +
					// robo.getName());
					GuiManager.getinstance().getMainTab().getTabs().add(robo.startup());
				} catch (Exception e) {
					logger.error("Error while starting plugin: " + robo.getName(), e);
				}
			}
			Thread.sleep(250);
		}

		// decide which port to use, if not set via cli, use setting from config
		Parameters parameters = getParameters();
		Map<String, String> namedArguments = parameters.getNamed();

		// we can process the following keys:
		// --port
		int port = 0; // default, not used
		for (Map.Entry<String, String> entry : namedArguments.entrySet()) {
			logger.info("found cli key " + entry.getKey());
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
//			LauncherImpl.notifyPreloader(this,
//					new AppPreloader.ActualTaskNotification("Wait for service startup completion."));
			Thread.sleep(50);
		}

		GuiManager.getinstance().writeLog("Server listening on port: " + usePort);
		} else {
			GuiManager.getinstance().writeLog("No REST server started!");
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// application title
		// TODO: I18N
		primaryStage.setTitle("Test Adaption Manager");

		// in stage einfuegen
		// width / height from config
		double width = GuiManager.getinstance().getConfig().getDouble("manager.width");
		double height = GuiManager.getinstance().getConfig().getDouble("manager.height");
		primaryStage.setScene(new Scene(GuiManager.getinstance().getMain(), width, height));

		// Close all -> shutdown all plugins
		primaryStage.setOnCloseRequest(evt -> {
			// execute own shutdown procedure
			shutdown(primaryStage);
		});

		// anzeigen
		primaryStage.show();
	}

	/**
	 * Shutdown application - call all plugins and tear down.
	 * 
	 * @param mainWindow
	 */
	private void shutdown(Stage mainWindow) {
		Iterator<TestAdaptionPlugin> plugins = ServiceLoader.load(TestAdaptionPlugin.class).iterator();
		while (plugins.hasNext()) {
			TestAdaptionPlugin robo = plugins.next();
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

		// close this...
		mainWindow.close();
	}

	public static void addTab(Tab aTab) {
		GuiManager.getinstance().getMainTab().getTabs().add(aTab);
	}
}
