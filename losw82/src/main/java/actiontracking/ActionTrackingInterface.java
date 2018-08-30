package actiontracking;

public interface ActionTrackingInterface {

	void redo(ActionTrackingWrapper wrapper);
	
	void undo(ActionTrackingWrapper wrapper);
}
