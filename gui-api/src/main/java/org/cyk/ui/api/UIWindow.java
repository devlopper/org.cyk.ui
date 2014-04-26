package org.cyk.ui.api;

import org.cyk.ui.api.form.UIForm;



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
	
	UIForm<FORM,OUTPUTLABEL,INPUT,SELECTITEM> newFormContainerInstance();
	
	UIForm<FORM,OUTPUTLABEL,INPUT,SELECTITEM> createFormContainer(Object anObjectModel);
}
