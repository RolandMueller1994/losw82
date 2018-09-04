package main.gui;

import main.gui.guielements.ExtendedChoiceBox;
import switchconfig.SwitchConfigDataHandler;
import switchconfig.SwitchConfigMainGUI;

public class BankChoiceBox extends ExtendedChoiceBox<Integer> {

	private SwitchConfigMainGUI switchConfigGUI;
	
	public BankChoiceBox(SwitchConfigMainGUI switchConfigGUI) {
		super("Bank: ", true);
		this.switchConfigGUI = switchConfigGUI;
		
		for(int i=0; i<SwitchConfigDataHandler.nrOfBanks; i++) {
			addChoice(i);
		}
		
		setValue(0);
	}
	
	@Override
	protected void stateChanged(Integer selected) {
		switchConfigGUI.changeBank(selected);
	}
}
