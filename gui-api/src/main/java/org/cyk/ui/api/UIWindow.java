package org.cyk.ui.api;

import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.event.EventBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.pattern.tree.DataTreeTypeBusiness;
import org.cyk.system.root.business.api.validation.ValidationPolicy;
import org.cyk.ui.api.command.menu.UIMenu;
import org.cyk.ui.api.model.event.AbstractEventCalendar;


public interface UIWindow<EDITOR,OUTPUTLABEL,INPUT,SELECTITEM> extends UIPart {
	
	AbstractUserSession getUserSession();
	
	ValidationPolicy getValidationPolicy();
	
	GenericBusiness getGenericBusiness();
	
	DataTreeTypeBusiness getDataTreeTypeBusiness();
	
	LanguageBusiness getLanguageBusiness();
	
	EventBusiness getEventBusiness();
	
	String getContentTitle();
	
	Boolean getShowContentMenu();
	
	Boolean getShowContextualMenu();
	
	Boolean getShowMainMenu();
	Boolean getShowDetailsMenu();
	
	Boolean getShowWindowHierachyMenu();
	
	Boolean getRenderedAsDialog();
	
	String getText();
	void setText(String text);
	
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
	
	UIMenu getWindowHierachyMenu();
	
	void setWindowHierachyMenu(UIMenu aMenu);
	
	/*
	<DATA> HierarchycalData<DATA> hierarchyInstance();
	
	<DATA> HierarchycalData<DATA> hierarchyInstance(Class<DATA> aDataClass);
	*/
	AbstractEventCalendar eventCalendarInstance(Class<?> aDataClass);
	
	AbstractEventCalendar eventCalendarInstance();
	
}
