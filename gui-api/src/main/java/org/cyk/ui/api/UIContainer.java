package org.cyk.ui.api;

import java.util.Collection;

/**
 * A view container is views wrapper in order to interact with them.
 * @author Komenan Y .Christian
 *
 */
public interface UIContainer {
	
	/**
	 * Get the title
	 * @return
	 */
	String getTitle();
	
	/**
	 * Get the views
	 * @return
	 */
	Collection<UIView> getViews();
	
	Object getObjectModel();
	
	/**
	 * The UI message manager
	 * @return
	 */
	UIMessageManager getMessageManager();
	
	UIView build(Object object);
	

}
