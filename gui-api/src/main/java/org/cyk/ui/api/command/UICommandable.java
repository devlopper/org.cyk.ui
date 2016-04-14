package org.cyk.ui.api.command;

import java.io.Serializable;
import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.business.impl.AbstractOutputDetails;

public interface UICommandable {
	 
	UICommand getCommand();
	void setCommand(UICommand aCommand);
	
	String getIdentifier();
	void setIdentifier(String anIdentifier);
	
	Integer getIndex();
	void setIndex(Integer index);
	
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
	
	NavigationMode getNavigationMode();
	void setNavigationMode(NavigationMode aNavigationMode);
	
	Collection<Parameter> getParameters();
	void setParameters(Collection<Parameter> theParameters);
	UICommandable addParameter(String name,Object value);
	UICommandable setParameter(String name,Object value);
	Parameter getParameter(String name);
	 
	UICommandable addParameter(AbstractIdentifiable identifiable);
	
	UICommandable addChild(String labelId,IconType iconType,String viewId,Collection<Parameter> parameters);
	UICommandable addChild(String labelId,IconType iconType,ViewType viewType,Collection<Parameter> parameters);
	void addChild(UICommandable aCommandable);
	
	UICommandable addCrudParameters(String crudParameter,AbstractIdentifiable identifiable,AbstractOutputDetails<?> details);
	UICommandable addCrudParameters(String crudParameter,AbstractIdentifiable identifiable);
	
	UICommandable addCreateParameters(Class<? extends AbstractIdentifiable> identifiableClass);
	UICommandable addReadAllParameters(Class<? extends AbstractIdentifiable> identifiableClass);
	
	UICommandable addPreviousViewParameter();
	UICommandable addDefaultParameters();
	
	/* Events */
	
	String getOnClick();
	void setOnClick(String value);
	
	/**/
	
	public enum RenderType{BUTTON,LINK}
	
	public enum CommandRequestType{UI_VIEW,BUSINESS_PROCESSING}
	
	public enum NavigationMode{DEFAULT,CLICK}
	
	public enum EventListener{NONE,CLICK}
	
	public enum ProcessGroup{THIS,FORM,PARTIAL}
	
	public enum ViewType{
		DYNAMIC_CRUD_ONE,DYNAMIC_CRUD_MANY,
		USERACCOUNT_LOGOUT,
		LICENCE,
		LICENCE_READ,
		TOOLS_AGENDA,
		TOOLS_REPORT,
		@Deprecated
		TOOLS_EXPORT_DATA_TABLE_TO_PDF,
		@Deprecated
		TOOLS_EXPORT_DATA_TABLE_TO_XLS,
		TOOLS_PRINT_DATA_TABLE,
		MODULE_REFERENCE_ENTITY,
		MODULE_SECURITY,
		USER_ACCOUNTS,
		ROLES,
		USER_ACCOUNT_CRUD_ONE,
		USER_ACCOUNT_CONSULT,
		USER_ACCOUNT_CHANGE_PASSWORD,
		EVENT_CRUD_ONE,
		NOTIFICATIONS, 
		HOME,
	}
	
	@Getter @Setter @AllArgsConstructor
	public static class Parameter implements Serializable{

		private static final long serialVersionUID = -9159625678100456054L;
		
		private String name;
		private Object value;
	}
	
}
