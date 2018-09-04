package switchconfig;

import java.util.HashMap;
import java.util.HashSet;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import main.gui.guielements.ExtendedTextField;
import xmlreader.XMLElement;

public class LoopConfigGUI extends GridPane {
	
	private HashMap<String, HashSet<LoopConfigInterface>> listeners;
	private static LoopConfigGUI instance;
	
	private ExtendedTextField loop0;
	private ExtendedTextField loop1;
	private ExtendedTextField loop2;
	private ExtendedTextField loop3;
	private ExtendedTextField loop4;
	private ExtendedTextField loop5;
	private ExtendedTextField loop6;
	private ExtendedTextField loop7;
	private ExtendedTextField ab1;
	private ExtendedTextField ab2;
	
	
	public static LoopConfigGUI getInstance() {
		if(instance == null) {
			instance = new LoopConfigGUI();
		}
		return instance;
	}
	
	private LoopConfigGUI() {
		listeners = new HashMap<>();
		
		setPadding(new Insets(5));
		setHgap(5);
		setVgap(5);
		
		for(int i=0; i<8; i++) {
			add(new Label("Loop "+ i +":"), 0, i);
		}
		
		add(new Label("AB 1:"), 0, 8);
		add(new Label("AB 2:"), 0, 9);
		
		loop0 = new ExtendedTextField("Loop 0", true) {
			
			protected synchronized void stateChanged() {
				fireLoopConfigChanged("Loop 0", getText());
			}
		};
		add(loop0, 1, 0);
		
		loop1 = new ExtendedTextField("Loop 1", true) {
			
			protected synchronized void stateChanged() {
				fireLoopConfigChanged("Loop 1", getText());
			}
		};
		add(loop1, 1, 1);
		
		loop2 = new ExtendedTextField("Loop 2", true) {
			
			protected synchronized void stateChanged() {
				fireLoopConfigChanged("Loop 2", getText());
			}
		};
		add(loop2, 1, 2);

		loop3 = new ExtendedTextField("Loop 3", true) {
	
			protected synchronized void stateChanged() {
				fireLoopConfigChanged("Loop 3", getText());
			}
		};
		add(loop3, 1, 3);

		loop4 = new ExtendedTextField("Loop 4", true) {
	
			protected synchronized void stateChanged() {
				fireLoopConfigChanged("Loop 4", getText());
			}
		};
		add(loop4, 1, 4);
		
		loop5 = new ExtendedTextField("Loop 5", true) {
	
			protected synchronized void stateChanged() {
				fireLoopConfigChanged("Loop 5", getText());
			}
		};
		add(loop5, 1, 5);
		
		loop6 = new ExtendedTextField("Loop 6", true) {
			
			protected synchronized void stateChanged() {
				fireLoopConfigChanged("Loop 6", getText());
			}
		};
		add(loop6, 1, 6);
		
		loop7 = new ExtendedTextField("Loop 7", true) {
			
			protected synchronized void stateChanged() {
				fireLoopConfigChanged("Loop 7", getText());
			}
		};
		add(loop7, 1, 7);
		
		ab1 = new ExtendedTextField("AB 1", true) {
			
			protected synchronized void stateChanged() {
				fireLoopConfigChanged("AB 1", getText());
			}
		};
		add(ab1, 1, 8);
		
		ab2 = new ExtendedTextField("AB 2", true) {
			
			protected synchronized void stateChanged() {
				fireLoopConfigChanged("AB 2", getText());
			}
		};
		add(ab2, 1, 9);
		
	}
	
	public synchronized void registerLoopConfigListener(LoopConfigInterface listener, String key) {
		if(!listeners.containsKey(key)) {
			listeners.put(key, new HashSet<>());
		}
		
		listeners.get(key).add(listener);
	}
	
	public synchronized void removeLoopConfigListener(LoopConfigInterface listener, String key) {
		if(listeners.containsKey(key)) {
			listeners.get(key).remove(listener);
			if(listeners.get(key).size() == 0) {
				listeners.remove(key);
			}
		}
 	}
	
	private synchronized void fireLoopConfigChanged(String key, String name) {
		if(listeners.containsKey(key)) {
			for(LoopConfigInterface listener : listeners.get(key)) {
				listener.NameChanged(name);
			}
		}
	}
	
	public void getXMLConfig(XMLElement parent) {
		XMLElement loop0Elem = new XMLElement("loop0");
		loop0Elem.setTextContent(loop0.getText());
		
		XMLElement loop1Elem = new XMLElement("loop1");
		loop1Elem.setTextContent(loop1.getText());
		
		XMLElement loop2Elem = new XMLElement("loop2");
		loop2Elem.setTextContent(loop2.getText());
		
		XMLElement loop3Elem = new XMLElement("loop3");
		loop3Elem.setTextContent(loop3.getText());
		
		XMLElement loop4Elem = new XMLElement("loop4");
		loop4Elem.setTextContent(loop4.getText());
		
		XMLElement loop5Elem = new XMLElement("loop5");
		loop5Elem.setTextContent(loop5.getText());
		
		XMLElement loop6Elem = new XMLElement("loop6");
		loop6Elem.setTextContent(loop6.getText());
	
		XMLElement loop7Elem = new XMLElement("loop7");
		loop7Elem.setTextContent(loop7.getText());
		
		XMLElement ab1Elem = new XMLElement("ab1");
		ab1Elem.setTextContent(ab1.getText());
		
		XMLElement ab2Elem = new XMLElement("ab1");
		ab2Elem.setTextContent(ab2.getText());
		
		parent.addElement(loop0Elem);
		parent.addElement(loop1Elem);
		parent.addElement(loop2Elem);
		parent.addElement(loop3Elem);
		parent.addElement(loop4Elem);
		parent.addElement(loop5Elem);
		parent.addElement(loop6Elem);
		parent.addElement(loop7Elem);
		parent.addElement(ab1Elem);
		parent.addElement(ab2Elem);
	}
	
	public void setXMLConfig(XMLElement elem) {
		
		for(XMLElement swElem : elem.getElements()) {
			if(swElem.getTag() == "loop0") {
				loop0.eventFromActionTracking = true;
				loop0.setText(swElem.getTextContent());
			} else if(swElem.getTag() == "loop1") {
				loop1.eventFromActionTracking = true;
				loop1.setText(swElem.getTextContent());
			} else if(swElem.getTag() == "loop2") {
				loop2.eventFromActionTracking = true;
				loop2.setText(swElem.getTextContent());
			} else if(swElem.getTag() == "loop3") {
				loop3.eventFromActionTracking = true;
				loop3.setText(swElem.getTextContent());
			} else if(swElem.getTag() == "loop4") {
				loop4.eventFromActionTracking = true;
				loop4.setText(swElem.getTextContent());
			} else if(swElem.getTag() == "loop5") {
				loop5.eventFromActionTracking = true;
				loop5.setText(swElem.getTextContent());
			} else if(swElem.getTag() == "loop6") {
				loop6.eventFromActionTracking = true;
				loop6.setText(swElem.getTextContent());
			} else if(swElem.getTag() == "loop7") {
				loop7.eventFromActionTracking = true;
				loop7.setText(swElem.getTextContent());
			} else if(swElem.getTag() == "ab1") {
				ab1.eventFromActionTracking = true;
				ab1.setText(swElem.getTextContent());
			} else if(swElem.getTag() == "ab2") {
				ab2.eventFromActionTracking = true;
				ab1.setText(swElem.getTextContent());
			}
		}
		
	}
}
