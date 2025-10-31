package org.opental.core;

import java.util.Iterator;

import org.apache.commons.configuration2.CombinedConfiguration;
import org.apache.commons.configuration2.tree.OverrideCombiner;
import org.opental.core.model.ConfigurationCatalog;
import org.opental.core.model.LogEntryCatalog;
import org.opental.core.sut.SutStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class GuiManager {

	private static final Logger logger = LoggerFactory.getLogger(GuiManager.class);

	private static final GuiManager gui = new GuiManager();

	/**
	 * general config storage
	 * - settings for each plugin
	 * - plugin specific root
	 * - keyword registry
	 */
	private CombinedConfiguration config = new CombinedConfiguration(new OverrideCombiner());
	
	private static final TableView<ConfigurationCatalog> configList = new TableView<>();
	private static final ObservableList<ConfigurationCatalog> configData = FXCollections.observableArrayList();

	private static final TableView<LogEntryCatalog> logList = new TableView<>();
	private static final ObservableList<LogEntryCatalog> logData = FXCollections.observableArrayList();
	
	private TabPane mainTabPane = new TabPane();

	private BorderPane mainPane = new BorderPane();
	
	public GuiManager() {
		logger.debug("Guimanager created.");
	}

	public static GuiManager getinstance() {
		return gui;
	}
	
	public TabPane getMainTab() {
		return mainTabPane;
	}
	
	public BorderPane getMain() {
		return mainPane;
	}
		
	public void writeLog(String someInfo) {
		final LogEntryCatalog tLC = new LogEntryCatalog(someInfo);
		
		Platform.runLater(() -> 
				logData.add(tLC)
		);
	}
	
	public void setTabStatus(String anIdentifier, SutStatus aStatus) {
		ObservableList<Tab> tabs = mainTabPane.getTabs();
		boolean tabFound = false;
		for (final Tab aTab : tabs) {
			if (aTab.getId().equals(anIdentifier)) {
				tabFound = true;
				if (aStatus == SutStatus.CONNECTED) {
					final Image imgTabStatus = new Image("/images/gui/transfer-2x.png");
					Platform.runLater(() -> aTab.setGraphic(new ImageView(imgTabStatus)));
				} else {
					final Image imgTabStatus = new Image("/images/gui/ban-2x.png");
					Platform.runLater(() -> aTab.setGraphic(new ImageView(imgTabStatus)));
				}
			}
		}
		if (!tabFound) {
			logger.warn("No tab found with identifier {}!", anIdentifier);
		}
	}
	
	public CombinedConfiguration getConfig() {
		return config;
	}
	
	public void showConfig() {
		ObservableList<Tab> tabs = mainTabPane.getTabs();
		for (final Tab aTab : tabs) {
			if (aTab.getId().equals("config")) {
				Iterator<String> keys = config.getKeys();
				while(keys.hasNext()) {
					String aKey = keys.next();
					final ConfigurationCatalog tCC = new ConfigurationCatalog(aKey, config.getString(aKey));
					
					Platform.runLater(() ->
						configData.add(tCC)
					);
				}
			}
		}
	}
	
	public Tab createConfigTab() {
		Tab genericTab = new Tab();
		genericTab.setText("Config");
		genericTab.setId("config");
		
		BorderPane root = new BorderPane();
		// Table bauen
		TableColumn<ConfigurationCatalog, String> confKey = new TableColumn<>("Key");
		confKey.setCellValueFactory(new PropertyValueFactory<>("key"));
		confKey.setSortable(false);
		confKey.setMinWidth(configList.getPrefWidth() * 0.25);
		
		TableColumn<ConfigurationCatalog, String> confValue = new TableColumn<>("Value");
		confValue.setCellValueFactory(new PropertyValueFactory<>("value"));
		confValue.setSortable(false);
		confValue.setMinWidth(configList.getPrefWidth() * 0.25);
		
		configList.setItems(configData);
		configList.getColumns().addAll(confKey, confValue);
		
		root.setCenter(configList);
		genericTab.setContent(root);
		
		return genericTab;
	}
	
	public Tab createLogTab() {
		logger.info("create logger tab");
		Tab loggerTab = new Tab();
		loggerTab.setId("log");
		loggerTab.setText("Log");
		
		BorderPane root = new BorderPane();
		// Table bauen
		TableColumn<LogEntryCatalog, String> logEntry = new TableColumn<>("Log Entry");
		logEntry.setCellValueFactory(new PropertyValueFactory<>("entry"));
		logEntry.setSortable(false);
		logEntry.setMinWidth(logList.getPrefWidth());
				
		logList.setItems(logData);
		logList.getColumns().addAll(logEntry);
		
		root.setCenter(logList);
		loggerTab.setContent(root);
		
		return loggerTab;
	}
}
