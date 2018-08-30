package projectfile;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import xmlreader.XMLElement;
import xmlreader.XMLHandler;

public class ProjectFileHandler {
	
	private XMLHandler projectXML;
	
	public XMLElement loadProjectFile(String path) {
		try {
			projectXML = new XMLHandler(path);
			return projectXML.getRootElement();
			// TODO insert to gui
		} catch (ParserConfigurationException | SAXException | IOException e) {
			return null;
		}
	}
	
	public void saveProjectFile(String path, XMLElement root) {
		
		projectXML = new XMLHandler(root);
		projectXML.writeToNewFile(path);
	}
}
