package actiontracking;

import i18n.LanguageResourceHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import resourceframework.ResourceProviderException;

public class UndoMenuItem extends MenuItem implements UndoQueueListener {
	
	private static final String TITLE = "title";

	public UndoMenuItem() throws ResourceProviderException {
		super(LanguageResourceHandler.getInstance().getLocalizedText(UndoMenuItem.class, TITLE));
		
		setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				UndoQueue.getInstance().undo();
			}
		});
		
		setDisable(true);
		UndoQueue.getInstance().addListener(this);
	}
	
	@Override
	public void undoEmpty() {
	
		setDisable(true);
	}

	@Override
	public void undoElementsPresent() {
		
		setDisable(false);
	}

}
