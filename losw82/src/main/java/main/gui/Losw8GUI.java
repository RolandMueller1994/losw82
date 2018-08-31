package main.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.gui.guielements.ExtendedCheckBox;
import main.gui.guielements.ExtendedRadioButtonGroup;
import main.gui.guielements.ExtendedTextField;
import projectfile.ProjectFileAccessPoint;

public class Losw8GUI extends Application {

	private static Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		this.primaryStage = primaryStage;
		
		VBox mainBox = new VBox();
		
		MenuBarCreator menuBarCreator = new MenuBarCreator();
		MenuBar menuBar = menuBarCreator.getMenuBar();
		
		mainBox.getChildren().add(menuBar);
		mainBox.getChildren().add(new ExtendedTextField("test", true));
		mainBox.getChildren().add(new ExtendedTextField("test", true));
		mainBox.getChildren().add(new Pane());
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
}
