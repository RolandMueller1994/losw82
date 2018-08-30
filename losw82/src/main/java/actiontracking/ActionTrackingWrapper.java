package actiontracking;

public class ActionTrackingWrapper {
	
	private Object reduElement;
	private Object unduElement;

	public ActionTrackingWrapper(Object reduElement, Object unduElement) {
		this.reduElement = reduElement;
		this.unduElement = unduElement;
	}
	
	public Object getReduElement() {
		return reduElement;
	}
	public void setReduElement(Object reduElement) {
		this.reduElement = reduElement;
	}
	public Object getUnduElement() {
		return unduElement;
	}
	public void setUnduElement(Object unduElement) {
		this.unduElement = unduElement;
	}
	
}
