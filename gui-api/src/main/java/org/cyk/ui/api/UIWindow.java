package org.cyk.ui.api;

import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.ui.api.command.UIMenu;
import org.cyk.ui.api.editor.Editor;
import org.cyk.ui.api.model.table.Table;


public interface UIWindow<EDITOR,OUTPUTLABEL,INPUT,SELECTITEM,TABLE extends Table<?>> extends UIPart {
	
	GenericBusiness getGenericBusiness();
	
	/**
	 * The UI message manager
	 * @return
	 */
	UIMessageManager getMessageManager();
	
	UIManager getUiManager();
	
	UIMenu getMainMenu();
	
	void setMainMenu(UIMenu aMenu);
	
	UIMenu getContextualMenu();
	
	void setContextualMenu(UIMenu aMenu);
	
	UIMenu getContentMenu();
	
	void setContentMenu(UIMenu aMenu);
	
	Editor<EDITOR,OUTPUTLABEL,INPUT,SELECTITEM> editorInstance();
	
	Editor<EDITOR,OUTPUTLABEL,INPUT,SELECTITEM> editorInstance(Object anObjectModel);
	
	<DATA> TABLE tableInstance();
	
	<DATA> TABLE tableInstance(Class<DATA> aDataClass);
}
