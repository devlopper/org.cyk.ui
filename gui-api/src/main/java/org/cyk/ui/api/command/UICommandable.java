package org.cyk.ui.api.command;

import java.io.Serializable;
import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
	
	Boolean getRequested();
	void setRequested(Boolean aValue);
	
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
	
	Object getViewId();
	void setViewId(Object aViewId);
	
	Collection<Parameter> getParameters();
	void setParameters(Collection<Parameter> theParameters);
	
	/**/
	
	public enum RenderType{BUTTON,LINK}
	
	public enum CommandRequestType{UI_VIEW,BUSINESS_PROCESSING}
	
	public enum EventListener{NONE,CLICK}
	
	public enum ProcessGroup{THIS,FORM,PARTIAL}
	
	public enum ViewType{
		DYNAMIC_CRUD_ONE,DYNAMIC_CRUD_MANY,
		USERACCOUNT_LOGOUT,
		LICENCE,
		TOOLS_CALENDAR,
		TOOLS_EXPORT_DATA_TABLE_TO_PDF,
		TOOLS_EXPORT_DATA_TABLE_TO_XLS,
		TOOLS_PRINT_DATA_TABLE,
	}
	
	public enum IconType{
		ACTION_GO_BACK,ACTION_OK,ACTION_APPLY,ACTION_SAVE,ACTION_ADD,ACTION_EDIT,ACTION_CANCEL,ACTION_REMOVE,ACTION_OPEN,ACTION_ADMINISTRATE,ACTION_HELP,ACTION_PREVIEW,
		ACTION_SEARCH,ACTION_EXPORT,ACTION_EXPORT_PDF,ACTION_EXPORT_EXCEL,ACTION_LOGOUT,ACTION_PRINT,
		
		THING_APPLICATION,THING_LICENCE,THING_TOOLS,THING_CALENDAR,THING_USERACCOUNT,
		
		PERSON,
	}
	
	
	@Getter @Setter @AllArgsConstructor
	public class Parameter implements Serializable{

		private static final long serialVersionUID = -9159625678100456054L;
		
		private String name;
		private Object value;
	}
	
}
