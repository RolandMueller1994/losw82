package switchconfig;

import main.gui.guielements.ExtendedRadioButton;

public class PresetRadioButton extends ExtendedRadioButton {

	private SwitchConfigWrapper switchConfig;
	
	public PresetRadioButton(SwitchConfigWrapper switchConfig) {
		super("Preset", true);
		
		this.switchConfig = switchConfig;
	}
	
	@Override
	public void stateChangedAction(boolean state) {
		switchConfig.setPreset(true);
	}
}
