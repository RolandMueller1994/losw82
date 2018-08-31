package main.gui.guielements;

import actiontracking.ActionTrackingInterface;
import actiontracking.ActionTrackingWrapper;
import actiontracking.RedoQueue;
import actiontracking.UndoQueue;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

public class ExtendedTextField extends TextField implements ActionTrackingInterface {

	private boolean actionTracking;
	private boolean eventFromActionTracking;
	
	private boolean undoInsertThreadRunning;
	private UndoInsertRunnable undoInsertRunnable;
	
	private String oldValue;
	
	public ExtendedTextField(String text, boolean actionTracking) {
		super(text);
		this.actionTracking = actionTracking;
		eventFromActionTracking = false;
		
		final KeyCombination undo = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
		final KeyCombination redo = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN);
		
		
		
		textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(eventFromActionTracking) {
					eventFromActionTracking = false;
				} else {
					checkInsert(oldValue, newValue);					
				}
			}
		});
		
		setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				
				if(undo.match(event)) {
					UndoQueue.getInstance().undo();
				} else if(redo.match(event)) {
					RedoQueue.getInstance().redo();
				}
				event.consume();
			}
		});
	}
	
	private synchronized void checkInsert(String oldValue, String newValue) {
		if(undoInsertThreadRunning) {
			undoInsertRunnable.setLastTime(System.currentTimeMillis());
		} else {
			undoInsertThreadRunning = true;
			this.oldValue = oldValue;
			undoInsertRunnable = new UndoInsertRunnable(this);
			
			Thread undoInsertThread = new Thread(undoInsertRunnable);
			undoInsertThread.start();
		}
	}
	
	private synchronized void insertUndo() {

		String newValue = getText();
		if(!newValue.equals(oldValue)) {
			UndoQueue.getInstance().insert(this, new ActionTrackingWrapper(newValue, oldValue));	
			oldValue = newValue;
			undoInsertThreadRunning = false;
			undoInsertRunnable = null;			
		}
	}
	
	

	@Override
	public void redo(ActionTrackingWrapper wrapper) {
		eventFromActionTracking = true;
		
		setText((String) wrapper.getReduElement());
		oldValue = (String) wrapper.getReduElement();
	}

	@Override
	public void undo(ActionTrackingWrapper wrapper) {
		eventFromActionTracking = true;
		
		setText((String) wrapper.getUnduElement());
	}
	
	private class UndoInsertRunnable implements Runnable {

		private static final long timeout = 200;
		private long lastTime;
		private ExtendedTextField parent;
		
		public UndoInsertRunnable(ExtendedTextField parent) {
			this.parent = parent;
		}
		
		public void setLastTime(long lastTime) {
			this.lastTime = lastTime;
		}
		
		@Override
		public void run() {
			
			lastTime = System.currentTimeMillis();
			
			while(System.currentTimeMillis() - lastTime < timeout) {
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			parent.insertUndo();
		}
		
	}

}
