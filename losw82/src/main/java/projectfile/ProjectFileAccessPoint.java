package projectfile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import actiontracking.RedoQueue;
import actiontracking.UndoQueue;
import i18n.LanguageResourceHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import main.gui.Losw8GUI;
import resourceframework.ResourceProviderException;
import xmlreader.XMLElement;

public class ProjectFileAccessPoint {

	private static final String FILE_CHOOSER_TITLE_SAVE = "fileChooserTitleSave";
	private static final String FILE_CHOOSER_TITLE_OPEN = "fileChooserTitleOpen";

	private static final String ASK_SAVE_TEXT = "askSaveText";
	private static final String ASK_SAVE_TITLE = "askSaveTitle";

	private ProjectFileHandler fileHandler;

	private XMLElement projectFileRoot;

	private String currentPath;
	private static boolean configChanged;

	private static ProjectFileAccessPoint instance;

	public static ProjectFileAccessPoint getInstance() {
		if (instance == null) {
			instance = new ProjectFileAccessPoint();
		}
		return instance;
	}

	private ProjectFileAccessPoint() {
		configChanged = false;
		fileHandler = new ProjectFileHandler();
	}

	public void setProjectChanged() {
		configChanged = true;
	}
	
	public void newProjectFile() {
		checkSave();
		
		currentPath = null;
		
		createDefaultConfig();
	}

	public void openProjectFile(String path) {

		checkSave();

		FileChooser fileChooser = new FileChooser();
		try {
			fileChooser.setTitle(LanguageResourceHandler.getInstance().getLocalizedText(ProjectFileAccessPoint.class,
					FILE_CHOOSER_TITLE_OPEN));
		} catch (ResourceProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		fileChooser.getExtensionFilters().add(new ExtensionFilter("losw82 Project Files", "*.losw82_proj"));
		File selectedFile = fileChooser.showOpenDialog(Losw8GUI.getPrimaryStage());
		if (selectedFile != null) {
			try {
				currentPath = selectedFile.getCanonicalPath();
				ProjectFileHandler handler = new ProjectFileHandler();
				projectFileRoot = handler.loadProjectFile(currentPath);
				
				UndoQueue.getInstance().clear();
				RedoQueue.getInstance().clear();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void saveProjectFile() {
		saveProjectFile(currentPath == null);
	}

	public void saveProjectFile(boolean newPath) {
		if (newPath) {
			FileChooser fileChooser = new FileChooser();
			try {
				fileChooser.setTitle(LanguageResourceHandler.getInstance()
						.getLocalizedText(ProjectFileAccessPoint.class, FILE_CHOOSER_TITLE_SAVE));
			} catch (ResourceProviderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			fileChooser.getExtensionFilters().add(new ExtensionFilter("losw82 Project Files", "*.losw82_proj"));
			File selectedFile = fileChooser.showSaveDialog(Losw8GUI.getPrimaryStage());
			if (selectedFile != null) {
				try {
					currentPath = selectedFile.getCanonicalPath();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		if (currentPath.contains(".")) {
			currentPath = currentPath.substring(0, currentPath.indexOf("."));
		}
		currentPath += ".losw82_proj";

		fileHandler.saveProjectFile(currentPath, projectFileRoot);
		configChanged = false;
		
		UndoQueue.getInstance().clear();
		RedoQueue.getInstance().clear();
	}

	public void createDefaultConfig() {
		checkSave();

		UndoQueue.getInstance().clear();
		RedoQueue.getInstance().clear();
		
		projectFileRoot = new XMLElement("losw82_proj");
	}

	public static void configChanged() {
		configChanged = true;
	}

	private void checkSave() {
		if (configChanged) {
			Alert askSaveAlert = new Alert(AlertType.CONFIRMATION);
			try {
				askSaveAlert.setContentText(LanguageResourceHandler.getInstance()
						.getLocalizedText(ProjectFileAccessPoint.class, ASK_SAVE_TEXT));
				askSaveAlert.setTitle(LanguageResourceHandler.getInstance()
						.getLocalizedText(ProjectFileAccessPoint.class, ASK_SAVE_TITLE));
				
				Optional<ButtonType> choice = askSaveAlert.showAndWait();
				if(choice.isPresent() && choice.get() == ButtonType.OK) {
					saveProjectFile();
				}
			} catch (ResourceProviderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
