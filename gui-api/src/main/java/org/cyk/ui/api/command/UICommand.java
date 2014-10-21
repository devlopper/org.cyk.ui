package org.cyk.ui.api.command;

import java.util.Collection;

import org.cyk.ui.api.UIMessageManager;

/**
 * An element on which user can interact to request something
 * @author Komenan Y .Christian
 *
 */
public interface UICommand {
	
	Collection<CommandListener> getCommandListeners();
		
	/**
	 * The code to be executed when validation has succeed
	 */
	Object execute(Object object);
		
	UIMessageManager getMessageManager();
	void setMessageManager(UIMessageManager messageManager);
	
}
