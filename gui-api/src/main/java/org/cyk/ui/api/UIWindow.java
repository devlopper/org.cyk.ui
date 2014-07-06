package org.cyk.ui.api;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.event.EventBusiness;
import org.cyk.system.root.business.api.pattern.tree.DataTreeTypeBusiness;
import org.cyk.ui.api.command.UIMenu;
import org.cyk.ui.api.editor.Editor;
import org.cyk.ui.api.model.EventCalendar;
import org.cyk.ui.api.model.table.Table;
import org.cyk.ui.api.model.table.Table.UsedFor;


public interface UIWindow<EDITOR,OUTPUTLABEL,INPUT,SELECTITEM,TABLE extends Table<?>> extends UIPart {
	
	GenericBusiness getGenericBusiness();
	
	DataTreeTypeBusiness getDataTreeTypeBusiness();
	
	EventBusiness getEventBusiness();
	
	String getContentTitle();
	
	Boolean getShowContentMenu();
	
	Boolean getShowContextualMenu();
	
	Boolean getShowMainMenu();
	
	Boolean getRenderedAsDialog();
	
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
	
	Editor<EDITOR,OUTPUTLABEL,INPUT,SELECTITEM> editorInstance(Object anObjectModel,Crud crud);
	
	<DATA> TABLE tableInstance();
	
	<DATA> TABLE tableInstance(Class<DATA> aDataClass,UsedFor usedFor,Crud crud);
	/*
	<DATA> HierarchycalData<DATA> hierarchyInstance();
	
	<DATA> HierarchycalData<DATA> hierarchyInstance(Class<DATA> aDataClass);
	*/
	EventCalendar eventCalendarInstance(Class<?> aDataClass);
	
	EventCalendar eventCalendarInstance();
}
