package main.gui;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import i18n.LanguageResourceHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import logging.CommonLogger;
import resourceframework.GlobalResourceProvider;
import resourceframework.ResourceProviderException;

public class MenuBarCreator {

	public MenuBar getMenuBar() throws ResourceProviderException, SAXException, IOException, ParserConfigurationException {
		MenuBar menuBar = new MenuBar();
		
		File menubarXML = new File((String) GlobalResourceProvider.getInstance().getResource("workDir") + File.separator + "menubar.xml");
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
		        .newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(menubarXML);
		
		// Get everything within tag menubar
		NodeList nodeList = document.getElementsByTagName("menubar");
		
		for(int i=0; i<nodeList.getLength(); i++) {
			Node menuBarNode = nodeList.item(i);
			
			// Read all menu tags in tag menubar
			if(menuBarNode.getNodeType() == Node.ELEMENT_NODE) {
				Element menuBarElement = (Element) menuBarNode;
				
				NodeList menuNodeList = menuBarElement.getChildNodes();
				
				for(int j=0; j<menuNodeList.getLength(); j++) {
					Node menuNode = menuNodeList.item(j);
					
					if(menuNode.getNodeType() == Node.ELEMENT_NODE) {
						Element menuElement = (Element) menuNode;
						
						Menu curMenu;
						try {
							curMenu = getMenu(menuElement);
							menuBar.getMenus().add(curMenu);
						} catch (DOMException | ClassNotFoundException | InstantiationException
								| IllegalAccessException e) {
							CommonLogger.getInstance().logException(e);
						}						
					}
				}
			}
		}
		
		return menuBar;
	}
	
	private Menu getMenu(Element menuElement) throws DOMException, ResourceProviderException, ClassNotFoundException, InstantiationException, IllegalAccessException {

		Menu curMenu = new Menu();
		
		NodeList childNodes = menuElement.getChildNodes();
		
		for(int i=0; i<childNodes.getLength(); i++) {
			Node curNode = childNodes.item(i);
			if(curNode.getNodeType()  == Node.ELEMENT_NODE) {
				Element curElement = (Element) curNode;
				
				if(curElement.getTagName().equals("title")) {
					String title = LanguageResourceHandler.getInstance().getLocalizedText(curElement.getTextContent());
					curMenu.setText(title);
				} else if(curElement.getTagName().equals("class")) {
					String className = curElement.getTextContent();
					if(className != null && !className.isEmpty()) {
						Class<?> itemClass = Class.forName(className);
						MenuItem curItem = (MenuItem) itemClass.newInstance();
						curMenu.getItems().add(curItem);								
					}
				} else if (curElement.getTagName().equals("separator")) {
					curMenu.getItems().add(new SeparatorMenuItem());
				} else {
					Menu childMenu = getMenu(curElement);
					curMenu.getItems().add(childMenu);
				}				
			}
		}
		
		return curMenu;
	}
	
}
