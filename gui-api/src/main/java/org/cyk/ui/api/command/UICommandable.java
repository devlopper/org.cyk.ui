package org.cyk.ui.api.command;

import java.io.Serializable;
import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.ui.api.Icon;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.config.OutputDetailsConfiguration;

public interface UICommandable {
	 
	UICommand getCommand();
	void setCommand(UICommand aCommand);
	
	String getIdentifier();
	UICommandable setIdentifier(String anIdentifier);
	
	Integer getIndex();
	void setIndex(Integer index);
	
	String getLabel();
	UICommandable setLabel(String label);
	
	Boolean getShowLabel();
	void setShowLabel(Boolean show);
	
	Icon getIcon();
	UICommandable setIcon(Icon anIcon);
	
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
	
	void addChild(UICommandable aCommandable);
	
	UICommandable addCrudParameters(Crud crud,AbstractIdentifiable identifiable,AbstractOutputDetails<?> details);
	UICommandable addCrudParameters(Crud crud,AbstractIdentifiable identifiable);
	
	UICommandable addCreateParameters(Class<? extends AbstractIdentifiable> identifiableClass);
	UICommandable addReadAllParameters(Class<? extends AbstractIdentifiable> identifiableClass);
	
	UICommandable addPreviousViewParameter();
	UICommandable addDefaultParameters();
	
	UICommandable addActionParameter(String actionIdentifier);
	
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
		DYNAMIC_CRUD_ONE,
		DYNAMIC_CRUD_MANY,
		USERACCOUNT_LOGOUT,
		TOOLS_AGENDA,
		TOOLS_REPORT,
		TOOLS_PRINT_DATA_TABLE,
		MODULE_REFERENCE_ENTITY,
		MODULE_SECURITY,
		USER_ACCOUNT_LIST,
		ROLES,
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
		
		/**/
		
		public static Parameter get(Collection<Parameter> parameters,String name) {
			for(Parameter parameter : parameters)
				if(parameter.getName().equals(name))
					return parameter;
			return null;
		}
		
		public static void setParameter(Collection<Parameter> parameters,String name, Object value) {
			Parameter parameter = get(parameters,name);
			if(parameter==null){
				parameter = new Parameter(name, value);
				add(parameters,name, value);
			}else{
				parameter.setValue(value);
			}
		}
		
		public static void add(Collection<Parameter> parameters,String name, Object value) {
			parameters.add(new Parameter(name, value));
		}
		
		public static void add(Collection<Parameter> parameters,AbstractIdentifiable identifiable) {
			if(identifiable==null)
				return;
			add(parameters,UIManager.getInstance().businessEntityInfos(identifiable.getClass()).getIdentifier(), identifiable.getIdentifier());
		}
		
		public static void addCrud(Collection<Parameter> parameters,Crud crud,AbstractIdentifiable identifiable, AbstractOutputDetails<?> details) {
			add(parameters,UniformResourceLocatorParameter.CLASS, UIManager.getInstance().keyFromClass(identifiable.getClass()));
			add(parameters,UniformResourceLocatorParameter.CRUD, Crud.CREATE.equals(crud) ? UniformResourceLocatorParameter.CRUD_CREATE :
				Crud.READ.equals(crud) ? UniformResourceLocatorParameter.CRUD_CREATE :
				Crud.UPDATE.equals(crud) ? UniformResourceLocatorParameter.CRUD_UPDATE : 
				Crud.DELETE.equals(crud) ? UniformResourceLocatorParameter.CRUD_DELETE : null);
			add(parameters,UniformResourceLocatorParameter.IDENTIFIABLE, identifiable.getIdentifier());
			if(details!=null){
				@SuppressWarnings("unchecked")
				OutputDetailsConfiguration configuration = UIManager.getInstance().findOutputDetailsConfiguration((Class<? extends AbstractOutputDetails<?>>) details.getClass());
				add(parameters,UniformResourceLocatorParameter.DETAILS, configuration.getRuntimeIdentifier());
			}
		}
		
		public static void addCrud(Collection<Parameter> parameters,Crud crud,AbstractIdentifiable identifiable) {
			addCrud(parameters,crud, identifiable, null);
		}
		
		public static void addCreate(Collection<Parameter> parameters,Class<? extends AbstractIdentifiable> identifiableClass) {
			add(parameters,UniformResourceLocatorParameter.CLASS, UIManager.getInstance().businessEntityInfos(identifiableClass).getIdentifier());
			add(parameters,UniformResourceLocatorParameter.CRUD, UniformResourceLocatorParameter.CRUD_CREATE);
		}
		
		public static void addReadAllParameters(Collection<Parameter> parameters,Class<? extends AbstractIdentifiable> identifiableClass) {
			add(parameters,UniformResourceLocatorParameter.CLASS, UIManager.getInstance().businessEntityInfos(identifiableClass).getIdentifier());
		}
		
		public static void addCreateOne(Collection<Parameter> parameters,Class<? extends AbstractIdentifiable> aClass){
			add(parameters,UniformResourceLocatorParameter.CLASS, UIManager.getInstance().keyFromClass(aClass));
			add(parameters,UniformResourceLocatorParameter.CRUD,UniformResourceLocatorParameter.CRUD_CREATE);
		}
	
		public static void addReport(Collection<Parameter> parameters,AbstractIdentifiable anIdentifiable,String reportIdentifier,String fileExtension,Boolean print,String windowMode){
			add(parameters,UniformResourceLocatorParameter.CLASS,UIManager.getInstance().keyFromClass(anIdentifiable.getClass()));
			add(parameters,UniformResourceLocatorParameter.IDENTIFIABLE,anIdentifiable.getIdentifier());
			add(parameters,UniformResourceLocatorParameter.FILE_EXTENSION,fileExtension);
			if(StringUtils.isNotBlank(windowMode))
				add(parameters,UniformResourceLocatorParameter.WINDOW_MODE,windowMode);
			add(parameters,UniformResourceLocatorParameter.REPORT_IDENTIFIER,reportIdentifier);
			add(parameters,UniformResourceLocatorParameter.VIEW_IDENTIFIER,UniformResourceLocatorParameter.VIEW_IDENTIFIER_REPORT);
			add(parameters,UniformResourceLocatorParameter.PRINT,Boolean.TRUE.equals(print));
			//System.out.println("UICommandable.Parameter.addReport() : "+parameters);
			//System.out.println(UIManager.getInstance().getParameterClass()+" : "+UIManager.getInstance().keyFromClass(anIdentifiable.getClass()));
		}
		
		@Override
		public String toString() {
			return name+"="+value;
		}
	}
	
}
