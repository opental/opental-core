package org.vebqa.vebtal;

import java.io.File;

import org.apache.commons.configuration2.CombinedConfiguration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.tree.OverrideCombiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vebqa.vebtal.model.CommandResult;
import org.vebqa.vebtal.model.CommandType;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public abstract class AbstractTestAdaptionPlugin implements TestAdaptionPlugin {

	private static final Logger logger = LoggerFactory.getLogger(AbstractTestAdaptionPlugin.class);
	
	protected TestAdaptionType adaptionType;

	protected AbstractTestAdaptionPlugin() {
		throw new UnsupportedOperationException("Use constructor without setting the adaption type is forbidden.");
	}

	protected AbstractTestAdaptionPlugin(TestAdaptionType aType) {
		this.adaptionType = aType;
	}

	public TestAdaptionType getType() {
		if (adaptionType == null) {
			throw new UnsupportedOperationException("Adaption type has to be defined before!");
		} else {
			return adaptionType;
		}
	}

	public Tab startup() {
		throw new UnsupportedOperationException("startup not yet implemented.");
	}

	public boolean shutdown() {
		throw new UnsupportedOperationException("shutdown not yet implemented.");
	}

	public Class<?> getImplementation() {
		throw new UnsupportedOperationException("not yet implemented.");
	}

	public String getAdaptionID() {
		throw new UnsupportedOperationException("not yet imlemented.");
	}

	public static void setDisableUserActions(boolean aState) {
	}

	protected Tab createTab(String aTabIdentifier, TableView<CommandResult> commandList,
			ObservableList<CommandResult> clData) {
		// Richtet den Plugin-spezifischen Tab ein

		Tab genericTab = new Tab();
		genericTab.setText(aTabIdentifier);
		genericTab.setId(aTabIdentifier);

		try {
			Image imgTabStatus = new Image("/images/gui/ban-2x.png");
			genericTab.setGraphic(new ImageView(imgTabStatus));
		} catch (IllegalArgumentException e) {
			logger.error("Cannot load icon from resource!");
		}

		// LogBox
		BorderPane root = new BorderPane();

		// Top bauen
		HBox hbox = new HBox();
		hbox.setId("table");

		// Table bauen
		TableColumn<CommandResult, CommandType> selCommandType = new TableColumn<>("Type");
		selCommandType.setCellValueFactory(new PropertyValueFactory<>("type"));
		selCommandType.setCellFactory((tableColumn) -> {
			TableCell<CommandResult, CommandType> tableCell = new TableCell<>() {

				private final VBox vbox;
				private final ImageView imageview;

				// Constructor
				{
					vbox = new VBox();
					vbox.setAlignment(Pos.CENTER);
					imageview = new ImageView();
					imageview.setFitHeight(16);
					imageview.setFitWidth(16);
					imageview.setVisible(true);
					imageview.setCache(true);
					vbox.getChildren().addAll(imageview);
					setGraphic(vbox);
				}

				@Override
				protected void updateItem(CommandType item, boolean empty) {

					// calling super here is very important - don't skip this!
					super.updateItem(item, empty);

					if (empty) {
						setText(null);
						setGraphic(null);

					} else {
						Image image;
						if (item == CommandType.ACCESSOR) {
							image = new Image("/images/gui/arrow-left-2x.png");
						} else if (item == CommandType.ACTION) {
							image = new Image("/images/gui/arrow-right-2x.png");
						} else if (item == CommandType.ASSERTION) {
							image = new Image("/images/gui/eye-2x.png");
						} else {
							image = new Image("/images/gui/question-mark-2x.png");
						}

						imageview.setImage(image);
						setGraphic(vbox);
					}
				}
			};
			return tableCell;
		});
		selCommandType.setSortable(false);
		selCommandType.setPrefWidth(36); // fixed width!

		TableColumn<CommandResult, String> selCommand = new TableColumn<>("Command");
		selCommand.setCellValueFactory(new PropertyValueFactory<>("command"));
		selCommand.setSortable(false);
		selCommand.setMinWidth(commandList.getPrefWidth() * 0.15);
		
		TableColumn<CommandResult, String> selTarget = new TableColumn<>("Target");
		selTarget.setCellValueFactory(new PropertyValueFactory<>("target"));
		selTarget.setSortable(false);
		selTarget.setMinWidth(commandList.getPrefWidth() * 0.15);
		
		TableColumn<CommandResult, String> selValue = new TableColumn<>("Value");
		selValue.setCellValueFactory(new PropertyValueFactory<>("value"));
		selValue.setSortable(false);
		selValue.setMinWidth(commandList.getPrefWidth() * 0.15);
		
		TableColumn<CommandResult, Image> selResult = new TableColumn<>("Result");
		selResult.setCellValueFactory(new PropertyValueFactory<>("result"));
		selResult.setSortable(false);
		selResult.setMinWidth(commandList.getPrefWidth() * 0.10);
		
		TableColumn<CommandResult, String> selInfo = new TableColumn<>("LogInfo");
		selInfo.setCellValueFactory(new PropertyValueFactory<>("loginfo"));
		selInfo.setSortable(false);
		selInfo.setMinWidth(commandList.getPrefWidth() * 0.45);
		
		commandList.setItems(clData);
		commandList.getColumns().addAll(selCommandType, selCommand, selTarget, selValue, selResult, selInfo);

		final ContextMenu tableContextMenu = new ContextMenu();
		
		final MenuItem clearMenuItem = new MenuItem("Clear all");
		Image imgClear = new Image("/images/gui/trash-2x.png");
		clearMenuItem.setGraphic(new ImageView(imgClear));
		clearMenuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				clData.clear();
			}
		});

		// we want to copy cells
		commandList.getSelectionModel().setCellSelectionEnabled(true);
		commandList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		final MenuItem copyMenuItem = new MenuItem("Copy");
		Image imgCopy = new Image("/images/gui/copy.png");
		copyMenuItem.setGraphic(new ImageView(imgCopy));
				
		copyMenuItem.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		        ObservableList<TablePosition> posList = commandList.getSelectionModel().getSelectedCells();
		        int old_r = -1;
		        StringBuilder clipboardString = new StringBuilder();
		        for (TablePosition p : posList) {
		            int r = p.getRow();
		            int c = p.getColumn();
		            Object cell = commandList.getColumns().get(c).getCellData(r);
		            if (cell == null)
		                cell = "";
		            if (old_r == r)
		                clipboardString.append('\t');
		            else if (old_r != -1)
		                clipboardString.append('\n');
		            clipboardString.append(cell);
		            old_r = r;
		        }
		        final ClipboardContent content = new ClipboardContent();
		        content.putString(clipboardString.toString());
		        Clipboard.getSystemClipboard().setContent(content);
		    }
		});
		
		tableContextMenu.getItems().addAll(clearMenuItem, copyMenuItem);
		commandList.setContextMenu(tableContextMenu);

		// einfuegen
		root.setTop(hbox);
		root.setCenter(commandList);
		genericTab.setContent(root);

		return genericTab;
	}
	
	protected CombinedConfiguration loadConfig(String aTabIdentifier) {
		Parameters params = new Parameters();
		
		// load default configuration from root
		String tPropertiesCoreName = aTabIdentifier + ".properties";
		File tPropertiesCore = new File("./" + tPropertiesCoreName);
		FileBasedConfigurationBuilder<FileBasedConfiguration> builderCore = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
				PropertiesConfiguration.class)
						.configure(params.properties().setFile(tPropertiesCore));
		FileBasedConfiguration configCore = null;

		try {
			configCore = builderCore.getConfiguration();
		} catch (ConfigurationException e) {
			logger.error("Couldnt load configuration file: {} because of {}", tPropertiesCore.getAbsolutePath(), e.getMessage());
		}
		// load user configuration from /conf folder
		String tPropertiesUserName = aTabIdentifier + "_user.properties";
		File tPropertiesUser = new File("./conf/" + tPropertiesUserName);
		FileBasedConfigurationBuilder<FileBasedConfiguration> builderUser = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
				PropertiesConfiguration.class)
						.configure(params.properties().setFile(tPropertiesUser));
		FileBasedConfiguration configUser = null;

		try {
			configUser = builderUser.getConfiguration();
		} catch (ConfigurationException e) {
			logger.error("Couldnt load configuration file: {} because of {}", tPropertiesUser.getAbsolutePath(), e.getMessage());
		}
		
		
		// use an override strategy
		CombinedConfiguration configCombined = new CombinedConfiguration(new OverrideCombiner());
		// load user configuration first
		if (configUser != null) {
			configCombined.addConfiguration(configUser);
		}
		// core config is added
		if (configCore != null) {
			configCombined.addConfiguration(configCore);
		}
		return configCombined;
	}	
}
