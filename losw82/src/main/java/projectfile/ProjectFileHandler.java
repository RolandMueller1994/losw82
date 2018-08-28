package projectfile;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import xmlreader.XMLHandler;

public class ProjectFileHandler {
	
	private XMLHandler projectXML;
	
	public void setupDefault() {
		// TODO get default from gui
	}
	
	public void loadProjectFile(String path) {
		try {
			projectXML = new XMLHandler(path);
			
			// TODO insert to gui
		} catch (ParserConfigurationException | SAXException | IOException e) {
			setupDefault();
		}
	}
	
	public void saveProjectFile(String path) {
		if(projectXML != null) {
			projectXML.writeToNewFile(path);
		}
	}
}
