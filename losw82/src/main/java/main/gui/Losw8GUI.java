package main.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.gui.guielements.ExtendedCheckBox;
import main.gui.guielements.ExtendedRadioButtonGroup;
import main.gui.guielements.ExtendedTextField;
import projectfile.ProjectFileAccessPoint;
import switchconfig.LoopConfigGUI;
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
		splitPane.getItems().add(new SwitchConfigMainGUI());
		splitPane.getItems().add(LoopConfigGUI.getInstance());
		
		mainBox.getChildren().add(splitPane);
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
