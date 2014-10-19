package org.cyk.ui.api.command;

import java.util.Collection;

import org.cyk.system.root.business.api.BusinessEntityInfos;

public interface UICommandable {
	 
	UICommand getCommand();
	
	void setCommand(UICommand aCommand);
	
	String getIdentifier();
	
	void setIdentifier(String anIdentifier);
	
	String getLabel();
	
	void setLabel(String label);
	
	Boolean getShowLabel();
	
	void setShowLabel(Boolean show);
	
	IconType getIconType();
	
	void setIconType(IconType anIconType);

	String getTooltip();
	
	void setTooltip(String label);
	
	RenderType getRenderType();
	
	void setRenderType(RenderType aRenderType);
	
	Boolean getRendered();
	
	void setRendered(Boolean aValue);
	
	CommandRequestType getCommandRequestType();
	
	void setCommandRequestType(CommandRequestType aCommandRequestType);
	
	EventListener getEventListener();
	
	void setEventListener(EventListener anEventListener);
	
	ProcessGroup getProcessGroup();
	
	void setProcessGroup(ProcessGroup aProcessGroup);
	
	Boolean getIsNavigationCommand();
	
	Collection<UICommandable> getChildren();
	
	void setChildren(Collection<UICommandable> commandables);
	
	BusinessEntityInfos getBusinessEntityInfos();
	
	void setBusinessEntityInfos(BusinessEntityInfos aBusinessEntityInfos);
	
	ViewType getViewType();
	
	void setViewType(ViewType aViewType);
	
	/**/
	
	public enum RenderType{BUTTON,LINK}
	
	public enum CommandRequestType{UI_VIEW,BUSINESS_PROCESSING}
	
	public enum EventListener{NONE,CLICK}
	
	public enum ProcessGroup{THIS,FORM,PARTIAL}
	
	public enum ViewType{
		DYNAMIC_CRUD_ONE,DYNAMIC_CRUD_MANY,
		USERACCOUNT_LOGOUT,
		MANAGEMENT_DEPLOYMENT,MANAGEMENT_LICENCE,
		TOOLS_CALENDAR
	}
	
	public enum IconType{
		ACTION_GO_BACK,ACTION_OK,ACTION_APPLY,ACTION_SAVE,ACTION_ADD,ACTION_EDIT,ACTION_CANCEL,ACTION_REMOVE,ACTION_OPEN,ACTION_ADMINISTRATE,ACTION_HELP,ACTION_PREVIEW,
		ACTION_SEARCH,ACTION_EXPORT,ACTION_EXPORT_PDF,ACTION_EXPORT_EXCEL,
		
		THING_TOOLS,THING_CALENDAR,THING_USERACCOUNT,
		
		PERSON,
	}
	
}
