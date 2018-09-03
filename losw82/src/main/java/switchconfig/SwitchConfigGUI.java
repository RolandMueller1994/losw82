package switchconfig;

import i18n.LanguageResourceHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import resourceframework.ResourceProviderException;

public class SwitchConfigGUI extends GridPane {

	private static final String SWITCH = "switch";
	
	private SwitchConfigWrapper switchConfig;
	private int switchNr;
	
	public SwitchConfigGUI(int switchNr) {
		this.switchNr = switchNr;
		this.switchConfig = new SwitchConfigWrapper(switchNr, false);
		
		try {
			add(new Label(LanguageResourceHandler.getInstance().getLocalizedText(SwitchConfigGUI.class, SWITCH) + " " + switchNr), 0, 0);
		} catch (ResourceProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		add(new PresetRadioButton(switchConfig), 0, 1);
		
		for(int i=0; i<8;i++) {
			add(new LoopStateRadioButton(i, switchConfig), 0, 2+i);
		}
		
		add(new ABStateRadioButton(true, switchConfig), 0, 10);
		add(new ABStateRadioButton(false, switchConfig), 0, 11);
		
		setPadding(new Insets(5));
		setHgap(5);
		setVgap(5);
		
		setBorder(new Border(new BorderStroke(null, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1))));
	}
	
}
