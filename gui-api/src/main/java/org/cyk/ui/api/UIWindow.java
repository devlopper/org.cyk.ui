package org.cyk.ui.api;

import org.cyk.ui.api.editor.Editor;
import org.cyk.ui.api.model.table.Table;


public interface UIWindow<FORM,OUTPUTLABEL,INPUT,SELECTITEM> extends UIPart {
	
	/**
	 * The UI message manager
	 * @return
	 */
	UIMessageManager getMessageManager();
	
	UIManager getUiManager();
	
	Editor<FORM,OUTPUTLABEL,INPUT,SELECTITEM> editorInstance();
	
	Editor<FORM,OUTPUTLABEL,INPUT,SELECTITEM> editorInstance(Object anObjectModel);
	
	<DATA> Table<DATA> tableInstance(Class<DATA> aDataClass);
}
