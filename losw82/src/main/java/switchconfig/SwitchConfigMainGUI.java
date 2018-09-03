package switchconfig;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

public class SwitchConfigMainGUI extends GridPane {

	public SwitchConfigMainGUI() {
		
		for(int i=0; i<10; i++) {
			add(new SwitchConfigGUI(i), i%5, 1-i/5);
		}
		
		setHgap(10);
		setVgap(10);
		setPadding(new Insets(10));
	}
	
}
