package switchconfig;

import main.gui.guielements.ExtendedRadioButton;

public class ABStateRadioButton extends ExtendedRadioButton implements LoopConfigInterface {

	private SwitchConfigWrapper switchConfig;
	private boolean ab1;
	
	public ABStateRadioButton(boolean ab1, SwitchConfigWrapper switchConfig) {
		super(true);
		if(ab1) {
			setText("AB 1");
			LoopConfigGUI.getInstance().registerLoopConfigListener(this, "AB 1");
		} else {
			setText("AB 2");
			LoopConfigGUI.getInstance().registerLoopConfigListener(this, "AB 2");
		}
		
		this.ab1 = ab1;
		setSwitchConfig(switchConfig);
	}
	
	@Override
	public void stateChangedAction(boolean state) {
		if(ab1) {
			switchConfig.setAb1(state);
		} else {
			switchConfig.setAb2(state);
		}
	}

	@Override
	public void NameChanged(String name) {
		setText(name);
	}
	
	public void setSwitchConfig(SwitchConfigWrapper switchConfig) {
		this.switchConfig = switchConfig;
		
		if(ab1) {
			if(isSelected() != switchConfig.isAb1()) {
				changeFromUndoRedo = true;
				setSelected(switchConfig.isAb1());
			}
		} else {
			if(isSelected() != switchConfig.isAb2()) {
				changeFromUndoRedo = true;
				setSelected(switchConfig.isAb2());
			}
		}
	}
}
