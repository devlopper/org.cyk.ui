package org.cyk.ui.api;


public interface UIPart {
	
	/**
	 * Get the title
	 * @return
	 */
	String getTitle();
	
	//TODO to be removed - use listener instead
	void targetDependentInitialisation();

}
