package switchconfig;

import main.gui.guielements.ExtendedChoiceBox;
import xmlreader.XMLElement;

public class BankChangeConfig extends ExtendedChoiceBox<String> {

	SwitchConfigMainGUI switchGUI;
	
	public BankChangeConfig(SwitchConfigMainGUI switchGUI) {
		super("Bank Change", true);
		this.switchGUI = switchGUI;
		
		addChoice("Fixed Bank 0");
		addChoice("Up/Down");
		addChoice("Long Press");
		
		setValue("Long Press");
	}
	
	@Override
	protected void stateChanged(String selected) {
		
		if(selected == "Up/Down") {
			switchGUI.inactiveSwitches(true);
			SwitchConfigDataHandler.getInstance().setInactive(true);
		} else {
			switchGUI.inactiveSwitches(false);
			SwitchConfigDataHandler.getInstance().setInactive(false);
		}
	}
	
	public void getXMLConfig(XMLElement parent) {
		parent.setTextContent(getValue());
	}
	
	public void setXMLConfig(XMLElement elem) {
		setValue(elem.getTextContent());
	}
}
