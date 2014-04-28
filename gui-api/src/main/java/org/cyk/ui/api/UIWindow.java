package org.cyk.ui.api;

import org.cyk.ui.api.form.UIForm;
import org.cyk.ui.api.model.table.Table;



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
	
	UIForm<FORM,OUTPUTLABEL,INPUT,SELECTITEM> formInstance();
	
	UIForm<FORM,OUTPUTLABEL,INPUT,SELECTITEM> formInstance(Object anObjectModel);
	
	<DATA> Table<DATA> tableInstance(Class<DATA> aDataClass);
}
