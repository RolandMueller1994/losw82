package main.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.gui.guielements.ExtendedCheckBox;
import main.gui.guielements.ExtendedRadioButtonGroup;
import main.gui.guielements.ExtendedTextField;
import projectfile.ProjectFileAccessPoint;
import switchconfig.BankChangeConfig;
import switchconfig.LoopConfigGUI;
import switchconfig.OverrideGUI;
import switchconfig.SwitchConfigDataHandler;
import switchconfig.SwitchConfigMainGUI;

public class Losw8GUI extends Application {

	private static Stage primaryStage;
	private static String fileTitle;
	private static boolean fileChanged;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		this.primaryStage = primaryStage;
		
		VBox mainBox = new VBox();
		
		MenuBarCreator menuBarCreator = new MenuBarCreator();
		MenuBar menuBar = menuBarCreator.getMenuBar();
		
		mainBox.getChildren().add(menuBar);
		
		SplitPane splitPane = new SplitPane();
		SwitchConfigMainGUI switchGUI = new SwitchConfigMainGUI();
		splitPane.getItems().add(switchGUI);
		splitPane.getItems().add(LoopConfigGUI.getInstance());
		
		mainBox.getChildren().add(splitPane);
		
		GridPane lowerGrid = new GridPane();
		HBox configBox = new HBox();
		
		BankChangeConfig bankChange = new BankChangeConfig(switchGUI);
		ProjectFileAccessPoint.getInstance().setBankChangeComponent(bankChange);
		
		BankChoiceBox bankChoice = new BankChoiceBox(switchGUI);
		configBox.getChildren().add(bankChoice);
		configBox.getChildren().add(bankChange);
		lowerGrid.add(configBox, 0, 0);
		OverrideGUI overrideGUI = new OverrideGUI();
		lowerGrid.add(overrideGUI, 0, 1);
		
		mainBox.getChildren().add(lowerGrid);
		Scene scene = new Scene(mainBox);
		
		ProjectFileAccessPoint.getInstance().createDefaultConfig();
		
		primaryStage.setScene(scene);
		primaryStage.setMaximized(true);
		primaryStage.show();
	}

	public void buildGUI(String[] args) {
		launch(args);
	}
	
	public static Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public static void setFileTitle(String title) {
		primaryStage.setTitle(title);
		fileTitle = title;
	}
	
	public static void setFileChanged(boolean changed) {
		fileChanged = changed;
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				if(fileChanged) {
					primaryStage.setTitle(fileTitle + " *");
				} else {
					primaryStage.setTitle(fileTitle);
				}
			}
		});
	}
}
