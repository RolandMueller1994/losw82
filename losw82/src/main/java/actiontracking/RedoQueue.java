package actiontracking;

import java.util.HashSet;
import java.util.LinkedList;

public class RedoQueue {
	
	private static RedoQueue instance;
	
	private LinkedList<InternallActionTrackingWrapper> queue;
	private HashSet<RedoQueueListener> listeners;
	
	public static RedoQueue getInstance() {
		if(instance == null) {
			instance = new RedoQueue();
		}
		return instance;
	}
	
	private RedoQueue() {
		queue = new LinkedList<>();
		listeners = new HashSet<>();
	}

	public synchronized void clear() {
		queue.clear();
		
		fireEmpty();
	}
	
	synchronized void insert(InternallActionTrackingWrapper wrapper) {
		queue.addFirst(wrapper);
		
		fireElementsPresent();
	}
	
	public synchronized void redo() {
		if(queue.size() > 0) {
			InternallActionTrackingWrapper wrapper = queue.removeFirst();
			ActionTrackingInterface element = wrapper.getElement();
			ActionTrackingWrapper data = wrapper.getWrapper();
			
			element.redo(data);
			
			UndoQueue.getInstance().insert(wrapper);
		}
		
		if(queue.size() == 0) {
			fireEmpty();
		}
	}
	
	public synchronized void addListener(RedoQueueListener listener) {
		listeners.add(listener);
	}
	
	public synchronized void removeListener(RedoQueueListener listener) {
		listeners.remove(listener);
	}
	
	private void fireEmpty() {
		for(RedoQueueListener listener : listeners) {
			listener.redoEmpty();
		}
	}
	
	private void fireElementsPresent() {
		for(RedoQueueListener listener : listeners) {
			listener.redoElementsPresent();
		}
	}
}
