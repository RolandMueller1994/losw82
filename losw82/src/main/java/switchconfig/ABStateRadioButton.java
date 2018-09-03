package switchconfig;

import main.gui.guielements.ExtendedRadioButton;

public class ABStateRadioButton extends ExtendedRadioButton {

	private SwitchConfigWrapper switchConfig;
	private boolean ab1;
	
	public ABStateRadioButton(boolean ab1, SwitchConfigWrapper switchConfig) {
		super(true);
		if(ab1) {
			setText("AB 1");			
		} else {
			setText("AB 2");
		}
		
		this.switchConfig = switchConfig;
		this.ab1 = ab1;
	}
	
	@Override
	public void stateChangedAction(boolean state) {
		if(ab1) {
			switchConfig.setAb1(state);
		} else {
			switchConfig.setAb2(state);
		}
	}
}
