package actiontracking;

import java.util.HashSet;
import java.util.LinkedList;

public class UndoQueue {
	
	private static UndoQueue instance;
	
	private final int undoDepth = 100;
	
	private HashSet<UndoQueueListener> listeners;
	private LinkedList<InternallActionTrackingWrapper> queue;
	
	public static UndoQueue getInstance() {
		if(instance == null) {
			instance = new UndoQueue();
		}
		return instance;
	}
	
	private UndoQueue() {
		listeners = new HashSet<> ();
		queue = new LinkedList<>();
	}

	public synchronized void clear() {
		queue.clear();
		
		fireEmpty();
	}
	
	public synchronized void insert(ActionTrackingInterface element, ActionTrackingWrapper data) {
		queue.addFirst(new InternallActionTrackingWrapper(element, data));
		RedoQueue.getInstance().clear();
		
		checkLength();
		fireElementsPresent();
	}
	
	synchronized void insert(InternallActionTrackingWrapper wrapper) {
		queue.addFirst(wrapper);
		
		checkLength();
		fireElementsPresent();
	}
	
	public synchronized void undo() {
		
		if(queue.size() > 0) {
			InternallActionTrackingWrapper wrapper = queue.removeFirst();
			
			ActionTrackingInterface element = wrapper.getElement();
			ActionTrackingWrapper data = wrapper.getWrapper();
			
			element.undo(data);			
			RedoQueue.getInstance().insert(wrapper);
		}
		
		if(queue.size() == 0) {
			fireEmpty();
		}		
	}
	
	public synchronized void addListener(UndoQueueListener listener) {
		listeners.add(listener);
	}
	
	public synchronized void removeListener(UndoQueueListener listener) {
		listeners.remove(listener);
	}
	
	private void checkLength() {
		if(queue.size() > undoDepth) {
			for(int i=0; i<queue.size()-undoDepth; i++) {
				queue.removeLast();
			}
		}
		
	}
	
	private void fireElementsPresent() {
		for(UndoQueueListener listener : listeners) {
			listener.undoElementsPresent();
		}
	}
	
	private void fireEmpty() {
		for(UndoQueueListener listener : listeners) {
			listener.undoEmpty();
		}
	}
}
