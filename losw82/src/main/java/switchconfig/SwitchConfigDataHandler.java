package switchconfig;

import java.util.HashMap;

import xmlreader.XMLElement;

public class SwitchConfigDataHandler {

	public static final int nrOfBanks = 8;
	public static final int nrOfSwitches = 10;
	
	private static SwitchConfigDataHandler instance;
	private HashMap<Integer, HashMap<Integer, SwitchConfigWrapper>> data;
	
	public static SwitchConfigDataHandler getInstance() {
		if(instance == null) {
			instance = new SwitchConfigDataHandler();
		}
		return instance;
	}
	
	private SwitchConfigDataHandler() {
		data = new HashMap<>();
		for(int i=0; i<nrOfBanks; i++) {
			HashMap<Integer, SwitchConfigWrapper> switchData = new HashMap<>();
			for(int j=0; j<nrOfSwitches; j++) {
				switchData.put(j, new SwitchConfigWrapper(j, false));
			}
			data.put(i, switchData);
		}
	}
	
	public HashMap<Integer, SwitchConfigWrapper> getSwitchConfigForBank(int bank) {
		return data.get(bank);
	}
	
	public void setInactive(boolean inactive) {
		for(HashMap<Integer, SwitchConfigWrapper> configMap : data.values()) {
			configMap.get(4).setInactive(inactive);
			configMap.get(9).setInactive(inactive);
		}
	}
	
	public void getXMLConfig(XMLElement parent) {
		
		for(Integer bank : data.keySet()) {
			XMLElement bankElement = new XMLElement("bank");
			parent.addElement(bankElement);
			XMLElement bankNrElement = new XMLElement("number");
			bankNrElement.setTextContent(bank.toString());
			bankElement.addElement(bankNrElement);
			
			XMLElement switchElements = new XMLElement("switches");
			
			for(Integer switchNr : data.get(bank).keySet()) {
				XMLElement switchElement = new XMLElement("sw" + switchNr.toString());
				
				data.get(bank).get(switchNr).getXMLConfig(switchElement);
				switchElements.addElement(switchElement);
			}
			
			bankElement.addElement(switchElements);
		}
		
	}
	
}
