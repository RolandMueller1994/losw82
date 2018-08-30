package actiontracking;

import i18n.LanguageResourceHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import resourceframework.ResourceProviderException;

public class RedoMenuItem extends MenuItem implements RedoQueueListener {

	private static final String TITLE = "title";
	
	public RedoMenuItem() throws ResourceProviderException {
		super(LanguageResourceHandler.getInstance().getLocalizedText(RedoMenuItem.class, TITLE));
		
		setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				RedoQueue.getInstance().redo();
			}
		});
		
		setDisable(true);
		RedoQueue.getInstance().addListener(this);
	}
	
	@Override
	public void redoEmpty() {
		
		setDisable(true);
	}

	@Override
	public void redoElementsPresent() {
		
		setDisable(false);
	}

}
