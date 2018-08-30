package main.gui.guielements;

import java.util.HashMap;
import java.util.HashSet;

import actiontracking.ActionTrackingInterface;
import actiontracking.ActionTrackingWrapper;
import actiontracking.UndoQueue;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class ExtendedRadioButtonGroup extends GridPane implements ActionTrackingInterface {

	private HashMap<RadioButton, String> buttons;
	private boolean eventFromUndoRedo = false;
	private boolean actionTracking;
	
	public ExtendedRadioButtonGroup(int limit, boolean vertical, boolean actionTracking, String... choices) {
		this.actionTracking = actionTracking;
		buttons = new HashMap<>();
		
		int count = 0;
		int count1 = 0;
		
		ToggleGroup group = new ToggleGroup();
		Insets insets = new Insets(5);
		
		setPadding(insets);
		setHgap(5);
		setVgap(5);
		
		boolean first = true;
		
		for(String choice : choices) {
			RadioButton button = new RadioButton(choice);
			buttons.put(button, choice);
			button.setToggleGroup(group);
			button.setSelected(first);
			first = false;
			if(vertical) {
				add(button, count1, count);				
			} else {
				add(button, count, count1);
			}
			
			count++;
			if(count >= limit) {
				count = 0;
				count1++;
			}
		}
	
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				
				toggledUndoInsert((RadioButton) newValue, (RadioButton) oldValue);
			}
		});
		
	}
	
	private void toggledUndoInsert(RadioButton newValue, RadioButton oldValue) {
		if(eventFromUndoRedo) {
			eventFromUndoRedo = false;
		} else {
			if(actionTracking) {
				UndoQueue.getInstance().insert(this, new ActionTrackingWrapper(newValue, oldValue));
			}
		}
	}
	
	@Override
	public void redo(ActionTrackingWrapper wrapper) {
		eventFromUndoRedo = true;
		
		((RadioButton) wrapper.getReduElement()).setSelected(true);
	}

	@Override
	public void undo(ActionTrackingWrapper wrapper) {
		eventFromUndoRedo = true;
		
		((RadioButton) wrapper.getUnduElement()).setSelected(true);
	}

}
