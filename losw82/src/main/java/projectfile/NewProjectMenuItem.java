package projectfile;

import i18n.LanguageResourceHandler;
import javafx.scene.control.MenuItem;
import resourceframework.ResourceProviderException;

public class NewProjectMenuItem extends MenuItem {

	private static final String TITLE = "title";
	
	public NewProjectMenuItem() throws ResourceProviderException {
		super(LanguageResourceHandler.getInstance().getLocalizedText(NewProjectMenuItem.class, TITLE));
	}
	
}
