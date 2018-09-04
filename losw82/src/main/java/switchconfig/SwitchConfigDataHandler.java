package switchconfig;

import java.util.HashMap;

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
	
}
