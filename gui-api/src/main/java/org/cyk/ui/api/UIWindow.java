package org.cyk.ui.api;

import org.cyk.ui.api.form.UIFormContainer;



public interface UIWindow<FORM,OUTPUTLABEL,INPUT,SELECTITEM> {
	
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
	
	UIManager getUiManager();
	
	UIFormContainer<FORM,OUTPUTLABEL,INPUT,SELECTITEM> newFormContainerInstance();
	
	UIFormContainer<FORM,OUTPUTLABEL,INPUT,SELECTITEM> createFormContainer(Object anObjectModel);
}
