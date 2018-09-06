package switchconfig;

import java.util.HashMap;
import java.util.LinkedList;

import i18n.LanguageResourceHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import resourceframework.ResourceProviderException;

public class SwitchConfigGUI extends GridPane {

	private static final String SWITCH = "switch";

	private SwitchConfigWrapper switchConfig;
	private int switchNr;

	private Label title;

	private HashMap<Integer, LoopStateRadioButton> loopStateButtons;
	private ABStateRadioButton ab1Button;
	private ABStateRadioButton ab2Button;
	private PresetRadioButton presetButton;

	public SwitchConfigGUI(int switchNr, SwitchConfigWrapper switchConfig) {
		this.switchNr = switchNr;
		this.switchConfig = switchConfig;
		loopStateButtons = new HashMap<>();

		try {
			title = new Label(LanguageResourceHandler.getInstance().getLocalizedText(SwitchConfigGUI.class, SWITCH)
					+ " " + switchNr);
			add(title, 0, 0);
		} catch (ResourceProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		presetButton = new PresetRadioButton(switchConfig);
		add(presetButton, 0, 1);

		for (int i = 0; i < 8; i++) {
			LoopStateRadioButton button = new LoopStateRadioButton(i, switchConfig);
			add(button, 0, 2 + i);
			loopStateButtons.put(i, button);
		}

		ab1Button = new ABStateRadioButton(true, switchConfig);
		add(ab1Button, 0, 10);
		ab2Button = new ABStateRadioButton(false, switchConfig);
		add(ab2Button, 0, 11);

		setPadding(new Insets(5));
		setHgap(5);
		setVgap(5);

		setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1))));
	}

	public void setSwitchConfig(SwitchConfigWrapper data) {
		this.switchConfig = data;

		presetButton.setSwitchConfig(data);
		ab1Button.setSwitchConfig(data);
		ab2Button.setSwitchConfig(data);

		for (LoopStateRadioButton loopButton : loopStateButtons.values()) {
			loopButton.setSwitchConfig(data);
		}
	}

	public void setInactive(boolean inactive) {
		for (LoopStateRadioButton button : loopStateButtons.values()) {
			button.setDisable(inactive);
		}

		ab1Button.setDisable(inactive);
		ab2Button.setDisable(inactive);

		presetButton.setDisable(inactive);
		title.setDisable(inactive);

		if (inactive) {
			setBorder(new Border(
					new BorderStroke(Color.GREY, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1))));
		} else {
			setBorder(new Border(
					new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1))));
		}
	}

}
