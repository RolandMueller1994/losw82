package switchconfig;

import main.gui.guielements.ExtendedRadioButton;

public class LoopStateRadioButton extends ExtendedRadioButton {
	
	private int loop;
	private SwitchConfigWrapper switchData;
	
	public LoopStateRadioButton(int loop, SwitchConfigWrapper switchData) {
		super("Loop " + loop, true);
		this.loop = loop;
		this.switchData = switchData;
	}

	@Override
	public void stateChangedAction(boolean state) {
		switchData.setLoopActive(loop, state);
	}
}
