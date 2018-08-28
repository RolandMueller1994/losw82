package projectfile;

import java.io.File;
import java.io.IOException;

import i18n.LanguageResourceHandler;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import main.gui.Losw8GUI;
import resourceframework.ResourceProviderException;

public class ProjectFileAccessPoint {

	private static final String FILE_CHOOSER_TITLE = "fileChooserTitle";

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
	}

	public void openProjectFile(String path) {
		checkSave();
	}

	public void saveProjectFile() {
		saveProjectFile(currentPath == null);
	}

	public void saveProjectFile(boolean newPath) {
		if (newPath) {
			FileChooser fileChooser = new FileChooser();
			try {
				fileChooser.setTitle(LanguageResourceHandler.getInstance().getLocalizedText(ProjectFileAccessPoint.class,
						FILE_CHOOSER_TITLE));
			} catch (ResourceProviderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			fileChooser.getExtensionFilters().add(new ExtensionFilter("losw82 Project Files", "*.losw82_proj"));
			File selectedFile = fileChooser.showSaveDialog(Losw8GUI.getPrimaryStage());
			try {
				currentPath = selectedFile.getCanonicalPath();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(currentPath.contains(".losw82_proj")) {
			currentPath = currentPath.substring(0, currentPath.indexOf(".losw82_proj"));
		}
		currentPath += ".losw82_proj";
		
		System.out.println(currentPath);
	}

	public void createDefaultConfig() {
		checkSave();
	}

	public static void configChanged() {
		configChanged = true;
	}

	private void checkSave() {

	}
}
