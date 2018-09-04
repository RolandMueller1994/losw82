package switchconfig;

import java.util.HashMap;
import java.util.HashSet;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import main.gui.guielements.ExtendedTextField;

public class LoopConfigGUI extends GridPane {
	
	private HashMap<String, HashSet<LoopConfigInterface>> listeners;
	private static LoopConfigGUI instance;
	
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
		
		add(new ExtendedTextField("Loop 0", true) {
			
			protected synchronized void stateChanged() {
				fireLoopConfigChanged("Loop 0", getText());
			}
		}, 1, 0);
		add(new ExtendedTextField("Loop 1", true) {
			
			protected synchronized void stateChanged() {
				fireLoopConfigChanged("Loop 1", getText());
			}
		}, 1, 1);
		add(new ExtendedTextField("Loop 2", true) {
			
			protected synchronized void stateChanged() {
				fireLoopConfigChanged("Loop 2", getText());
			}
		}, 1, 2);

		add(new ExtendedTextField("Loop 3", true) {
	
			protected synchronized void stateChanged() {
				fireLoopConfigChanged("Loop 3", getText());
			}
		}, 1, 3);

		add(new ExtendedTextField("Loop 4", true) {
	
			protected synchronized void stateChanged() {
				fireLoopConfigChanged("Loop 4", getText());
			}
		}, 1, 4);
		add(new ExtendedTextField("Loop 5", true) {
	
			protected synchronized void stateChanged() {
				fireLoopConfigChanged("Loop 5", getText());
			}
		}, 1, 5);
		add(new ExtendedTextField("Loop 6", true) {
			
			protected synchronized void stateChanged() {
				fireLoopConfigChanged("Loop 6", getText());
			}
		}, 1, 6);
		add(new ExtendedTextField("Loop 7", true) {
			
			protected synchronized void stateChanged() {
				fireLoopConfigChanged("Loop 7", getText());
			}
		}, 1, 7);
		add(new ExtendedTextField("AB 1", true) {
			
			protected synchronized void stateChanged() {
				fireLoopConfigChanged("AB 1", getText());
			}
		}, 1, 8);
		add(new ExtendedTextField("AB 2", true) {
			
			protected synchronized void stateChanged() {
				fireLoopConfigChanged("AB 2", getText());
			}
		}, 1, 9);
		
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
}
