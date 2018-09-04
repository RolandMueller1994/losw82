package switchconfig;

import main.gui.guielements.ExtendedRadioButton;

public class LoopStateRadioButton extends ExtendedRadioButton implements LoopConfigInterface {
	
	private int loop;
	private SwitchConfigWrapper switchData;
	
	public LoopStateRadioButton(int loop, SwitchConfigWrapper switchData) {
		super("Loop " + loop, true);
		this.loop = loop;
		
		LoopConfigGUI.getInstance().registerLoopConfigListener(this, "Loop " + loop);
		setSwitchConfig(switchData);
	}

	@Override
	public void stateChangedAction(boolean state) {
		switchData.setLoopActive(loop, state);
	}

	@Override
	public void NameChanged(String name) {
		setText(name);
	}
	
	public void setSwitchConfig(SwitchConfigWrapper switchConfig) {
		this.switchData = switchConfig;
		if(isSelected() != switchConfig.getLoopActive(loop)) {
			changeFromUndoRedo = true;
			setSelected(switchConfig.getLoopActive(loop));			
		}
	}
}
