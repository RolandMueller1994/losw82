package actiontracking;

class InternallActionTrackingWrapper {

	private ActionTrackingInterface element;
	private ActionTrackingWrapper wrapper;
	
	public InternallActionTrackingWrapper(ActionTrackingInterface element, ActionTrackingWrapper wrapper) {
		this.element = element;
		this.wrapper = wrapper;
	}

	public ActionTrackingInterface getElement() {
		return element;
	}

	public ActionTrackingWrapper getWrapper() {
		return wrapper;
	}
	
}
