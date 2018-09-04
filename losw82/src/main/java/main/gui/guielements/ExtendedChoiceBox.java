package main.gui.guielements;

import actiontracking.ActionTrackingInterface;
import actiontracking.ActionTrackingWrapper;
import actiontracking.UndoQueue;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class ExtendedChoiceBox<T> extends GridPane implements ActionTrackingInterface {

	private ChoiceBox<T> choice;
	private Label label;
	private boolean actionTracking;
	private boolean changedFromUndoRedo;
	
	public ExtendedChoiceBox(String label, boolean actionTracking) {
		int choiceColumn = 0;
		if(label != null) {
			choiceColumn = 1;
			this.label = new Label(label);
			add(this.label, 0, 0);
		}
		
		setPadding(new Insets(5));
		setHgap(5);
		setVgap(5);
		
		choice = new ChoiceBox<>();
		add(choice, choiceColumn, 0);
		
		choice.valueProperty().addListener(new ChangeListener<T>() {

			@Override
			public void changed(ObservableValue<? extends T> observable, T oldValue, T newValue) {
				
				stateChanged(newValue);
				if(actionTracking) {
					if(changedFromUndoRedo) {
						changedFromUndoRedo = false;
					} else {
						insertUndoRedo(oldValue, newValue);
					}
				}
			}
		});
		
		this.actionTracking = actionTracking;
	}
	
	public ExtendedChoiceBox(boolean actionTracking) {
		this(null, actionTracking);
	}
	
	private void insertUndoRedo(T oldValue, T newValue) {
		UndoQueue.getInstance().insert(this, new ActionTrackingWrapper(newValue, oldValue));
	}
	
	public void addChoice(T element) {
		choice.getItems().add(element);
	}
	
	public void setValue(T element) {
		if(element.equals(getValue())) {
			changedFromUndoRedo = true;
			choice.setValue(element);			
		}
	}
	
	public T getValue() {
		return choice.getValue();
	}
	
	protected void stateChanged(T selected) {
		
	}
	
	@Override
	public void redo(ActionTrackingWrapper wrapper) {
		changedFromUndoRedo = true;
		
		choice.setValue((T) wrapper.getReduElement());
	}

	@Override
	public void undo(ActionTrackingWrapper wrapper) {
		changedFromUndoRedo = true;
		
		choice.setValue((T) wrapper.getUnduElement());
	}

}
