package org.cyk.ui.api;


public interface UIWindow {
	
	/**
	 * Get the title
	 * @return
	 */
	String getTitle();
	
	/**
	 * The UI message manager
	 * @return
	 */
	UIMessageManager getMessageManager();

}
