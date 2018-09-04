package switchconfig;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class OverrideGUI extends GridPane {

	public OverrideGUI() {
		setPadding(new Insets(5));
		setHgap(5);
		setVgap(5);
		
		add(new Label("Override: "), 0, 0);
		
		for(int i=0; i<8; i++) {
			add(new OverrideRadioButton(i), i+1, 0);
		}
		
		add(new OverrideRadioButton(true), 9, 0);
		add(new OverrideRadioButton(false), 10, 0);
	}
	
}
