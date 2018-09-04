package switchconfig;

import main.gui.guielements.ExtendedRadioButton;

public class OverrideRadioButton extends ExtendedRadioButton implements LoopConfigInterface {
	
	private int loop;
	private boolean ab1;
	private boolean isLoop;
	
	public OverrideRadioButton(int loop) {
		super("Loop " + loop, false);
		
		this.loop = loop;
		LoopConfigGUI.getInstance().registerLoopConfigListener(this, "Loop " + loop);
		isLoop = true;
	}
	
	public OverrideRadioButton(boolean ab1) {
		super(false);
		if(ab1) {
			setText("AB 1");
			LoopConfigGUI.getInstance().registerLoopConfigListener(this, "AB 1");
		} else {
			setText("AB 2");
			LoopConfigGUI.getInstance().registerLoopConfigListener(this, "AB 2");
		}
		
		this.ab1 = ab1;
		isLoop = false;
	}

	@Override
	public void NameChanged(String name) {	
		setText(name);
	}
	
	@Override
	public void stateChangedAction(boolean state) {
		// TODO write to comHandler
	}
	
}
