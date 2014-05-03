package org.cyk.ui.api;

import org.cyk.ui.api.editor.Editor;
import org.cyk.ui.api.model.table.Table;


public interface UIWindow<EDITOR,OUTPUTLABEL,INPUT,SELECTITEM,TABLE extends Table<?>> extends UIPart {
	
	/**
	 * The UI message manager
	 * @return
	 */
	UIMessageManager getMessageManager();
	
	UIManager getUiManager();
	
	Editor<EDITOR,OUTPUTLABEL,INPUT,SELECTITEM> editorInstance();
	
	Editor<EDITOR,OUTPUTLABEL,INPUT,SELECTITEM> editorInstance(Object anObjectModel);
	
	<DATA> TABLE tableInstance();
	
	<DATA> TABLE tableInstance(Class<DATA> aDataClass);
}
