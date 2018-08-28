package projectfile;

import i18n.LanguageResourceHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import resourceframework.ResourceProviderException;

public class SaveAtMenuItem extends MenuItem {
	
	private static final String TITLE = "title";
	
	public SaveAtMenuItem() throws ResourceProviderException {
		super(LanguageResourceHandler.getInstance().getLocalizedText(SaveAtMenuItem.class, TITLE));
		
		super.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				ProjectFileAccessPoint.getInstance().saveProjectFile(true);
			}
		});
	}

}
