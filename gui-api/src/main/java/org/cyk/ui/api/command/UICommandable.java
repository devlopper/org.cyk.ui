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
	
	public enum ViewType{DYNAMIC_FORM_EDITOR,DYNAMIC_FORM_TABLE,DYNAMIC_FORM_HIERARCHY,MANAGEMENT_DEPLOYMENT,MANAGEMENT_LICENCE}
	
	public enum IconType{
		ACTION_ADD,ACTION_CANCEL,ACTION_REMOVE,ACTION_OPEN,ACTION_ADMINISTRATE,ACTION_HELP
	}
	
}
