package switchconfig;

import java.util.HashMap;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

public class SwitchConfigMainGUI extends GridPane {

	HashMap<Integer, SwitchConfigGUI> switchConfigGUI;

	public SwitchConfigMainGUI() {

		switchConfigGUI = new HashMap<>();
		for (int i = 0; i < 10; i++) {
			SwitchConfigGUI switchConfigGUIElement = new SwitchConfigGUI(i,
					SwitchConfigDataHandler.getInstance().getSwitchConfigForBank(0).get(i));
			add(switchConfigGUIElement, i % 5, 1 - i / 5);
			switchConfigGUI.put(i, switchConfigGUIElement);
		}

		setHgap(10);
		setVgap(10);
		setPadding(new Insets(10));
	}

	public void changeBank(int bankNr) {

		for (Integer switchNr : switchConfigGUI.keySet()) {
			switchConfigGUI.get(switchNr)
					.setSwitchConfig(SwitchConfigDataHandler.getInstance().getSwitchConfigForBank(bankNr).get(switchNr));
		}
	}

}
