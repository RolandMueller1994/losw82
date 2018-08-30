package main.gui;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyCombination.Modifier;
import logging.CommonLogger;
import resourceframework.GlobalResourceProvider;
import resourceframework.ResourceProviderException;

public class MenuBarCreator {
	
	private HashMap<String, Modifier> modifiers;
	
	public MenuBarCreator() {
		modifiers = new HashMap<>();
		modifiers.put("shift", KeyCombination.SHIFT_DOWN);
		modifiers.put("ctrl", KeyCombination.CONTROL_DOWN);
	}

	public MenuBar getMenuBar()
			throws ResourceProviderException, SAXException, IOException, ParserConfigurationException {
		MenuBar menuBar = new MenuBar();

		File menubarXML = new File(
				(String) GlobalResourceProvider.getInstance().getResource("workDir") + File.separator + "menubar.xml");
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(menubarXML);

		// Get everything within tag menubar
		NodeList nodeList = document.getElementsByTagName("menubar");

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node menuBarNode = nodeList.item(i);

			// Read all menu tags in tag menubar
			if (menuBarNode.getNodeType() == Node.ELEMENT_NODE) {
				Element menuBarElement = (Element) menuBarNode;

				NodeList menuNodeList = menuBarElement.getChildNodes();

				for (int j = 0; j < menuNodeList.getLength(); j++) {
					Node menuNode = menuNodeList.item(j);

					if (menuNode.getNodeType() == Node.ELEMENT_NODE) {
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

	private Menu getMenu(Element menuElement) throws DOMException, ResourceProviderException, ClassNotFoundException,
			InstantiationException, IllegalAccessException {

		Menu curMenu = new Menu();

		NodeList childNodes = menuElement.getChildNodes();

		for (int i = 0; i < childNodes.getLength(); i++) {
			Node curNode = childNodes.item(i);
			if (curNode.getNodeType() == Node.ELEMENT_NODE) {
				Element curElement = (Element) curNode;

				if (curElement.getTagName().equals("title")) {
					String title = LanguageResourceHandler.getInstance().getLocalizedText(curElement.getTextContent());
					curMenu.setText(title);
				} else if (curElement.getTagName().equals("class")) {
					String className = curElement.getTextContent();
					String shortCutAttr = curElement.getAttribute("shortcut");
					if (className != null && !className.isEmpty()) {
						Class<?> itemClass = Class.forName(className);
						MenuItem curItem = (MenuItem) itemClass.newInstance();

						if (!shortCutAttr.isEmpty()) {
							LinkedList<String> shortCuts = parseShortCut(shortCutAttr);
							LinkedList<Modifier> combinations = new LinkedList<>();
							while (shortCuts.size() > 1) {
								combinations.add(modifiers.get(shortCuts.removeFirst()));
							}

							curItem.setAccelerator(new KeyCodeCombination(KeyCode.getKeyCode(shortCuts.get(0).toUpperCase()),
									combinations.toArray(new Modifier[combinations.size()])));
						}

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

	private LinkedList<String> parseShortCut(String shortCutStr) {
		LinkedList<String> shortCuts = new LinkedList<>();

		while (shortCutStr.contains(":")) {
			shortCuts.add(shortCutStr.substring(0, shortCutStr.indexOf(":")));
			shortCutStr = shortCutStr.substring(shortCutStr.indexOf(":")+1);
		}

		shortCuts.add(shortCutStr);
		return shortCuts;
	}
}
