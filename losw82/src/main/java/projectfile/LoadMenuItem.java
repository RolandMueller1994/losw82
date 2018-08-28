package projectfile;

import i18n.LanguageResourceHandler;
import javafx.scene.control.MenuItem;
import resourceframework.ResourceProviderException;

public class LoadMenuItem extends MenuItem {

	private static final String TITLE = "title";
	
	public LoadMenuItem() throws ResourceProviderException {
		super(LanguageResourceHandler.getInstance().getLocalizedText(LoadMenuItem.class, TITLE));
	}
	
}
