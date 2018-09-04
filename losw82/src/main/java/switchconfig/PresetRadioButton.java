package switchconfig;

import main.gui.guielements.ExtendedRadioButton;

public class PresetRadioButton extends ExtendedRadioButton {

	private SwitchConfigWrapper switchConfig;
	
	public PresetRadioButton(SwitchConfigWrapper switchConfig) {
		super("Preset", true);
		
		setSwitchConfig(switchConfig);
	}
	
	@Override
	public void stateChangedAction(boolean state) {
		switchConfig.setPreset(state);
	}
	
	public void setSwitchConfig(SwitchConfigWrapper switchConfig) {
		this.switchConfig = switchConfig;
		if(isSelected() != switchConfig.isPreset()) {
			changeFromUndoRedo = true;
			
			setSelected(switchConfig.isPreset());			
		}
	}
}
