package main.gui.guielements;

import actiontracking.ActionTrackingInterface;
import actiontracking.ActionTrackingWrapper;
import actiontracking.UndoQueue;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;

public class ExtendedCheckBox extends CheckBox implements ActionTrackingInterface {

	private boolean changeFromUndoRedo = false;
	private boolean actionTracking;
	
	public ExtendedCheckBox(String arg, boolean actionTracking) {
		super(arg);
		this.actionTracking = actionTracking;
		
		selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!changeFromUndoRedo) {
					insertToUndo();					
				} else {
					changeFromUndoRedo = false;
				}
			}
		});
	}
	
	public ExtendedCheckBox(boolean actionTracking) {
		this.actionTracking = actionTracking;
		
		selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!changeFromUndoRedo) {
					insertToUndo();					
				} else {
					changeFromUndoRedo = false;
				}
			}
		});
	}

	private void insertToUndo() {
		if(actionTracking) {
			UndoQueue.getInstance().insert(this,
					new ActionTrackingWrapper(new Boolean(isSelected()), new Boolean(!isSelected())));			
		}
	}

	@Override
	public void redo(ActionTrackingWrapper wrapper) {
		changeFromUndoRedo = true;
		setSelected((Boolean) wrapper.getReduElement());
	}

	@Override
	public void undo(ActionTrackingWrapper wrapper) {
		changeFromUndoRedo = true;
		setSelected((Boolean) wrapper.getUnduElement());
	}
}
