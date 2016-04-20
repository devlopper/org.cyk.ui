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
import org.cyk.ui.api.Icon;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.config.OutputDetailsConfiguration;

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
	
	Icon getIcon();
	void setIcon(Icon anIcon);
	
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
			add(parameters,UIManager.getInstance().getClassParameter(), UIManager.getInstance().keyFromClass(identifiable.getClass()));
			add(parameters,UIManager.getInstance().getCrudParameter(), Crud.CREATE.equals(crud) ? UIManager.getInstance().getCrudCreateParameter() :
				Crud.READ.equals(crud) ? UIManager.getInstance().getCrudReadParameter() :
				Crud.UPDATE.equals(crud) ? UIManager.getInstance().getCrudUpdateParameter() : 
				Crud.DELETE.equals(crud) ? UIManager.getInstance().getCrudDeleteParameter() : null);
			add(parameters,UIManager.getInstance().getIdentifiableParameter(), identifiable.getIdentifier());
			if(details!=null){
				@SuppressWarnings("unchecked")
				OutputDetailsConfiguration configuration = UIManager.getInstance().findOutputDetailsConfiguration((Class<? extends AbstractOutputDetails<?>>) details.getClass());
				add(parameters,UIManager.getInstance().getDetailsParameter(), configuration.getRuntimeIdentifier());
			}
		}
		
		public static void addCrud(Collection<Parameter> parameters,Crud crud,AbstractIdentifiable identifiable) {
			addCrud(parameters,crud, identifiable, null);
		}
		
		public static void addCreate(Collection<Parameter> parameters,Class<? extends AbstractIdentifiable> identifiableClass) {
			add(parameters,UIManager.getInstance().getClassParameter(), UIManager.getInstance().businessEntityInfos(identifiableClass).getIdentifier());
			add(parameters,UIManager.getInstance().getCrudParameter(), UIManager.getInstance().getCrudCreateParameter());
		}
		
		public static void addReadAllParameters(Collection<Parameter> parameters,Class<? extends AbstractIdentifiable> identifiableClass) {
			add(parameters,UIManager.getInstance().getClassParameter(), UIManager.getInstance().businessEntityInfos(identifiableClass).getIdentifier());
		}
		
		public static void addCreateOne(Collection<Parameter> parameters,Class<? extends AbstractIdentifiable> aClass){
			add(parameters,UIManager.getInstance().getClassParameter(), UIManager.getInstance().keyFromClass(aClass));
			add(parameters,UIManager.getInstance().getCrudParameter(),UIManager.getInstance().getCrudCreateParameter());
		}
	
		public static void addReport(Collection<Parameter> parameters,AbstractIdentifiable anIdentifiable,String reportIdentifier,String fileExtension,Boolean print,String windowMode){
			add(parameters,UIManager.getInstance().getParameterClass(),UIManager.getInstance().keyFromClass(anIdentifiable.getClass()));
			add(parameters,UIManager.getInstance().getParameterIdentifiable(),anIdentifiable.getIdentifier());
			add(parameters,UIManager.getInstance().getParameterFileExtension(),fileExtension);
			if(StringUtils.isNotBlank(windowMode))
				add(parameters,UIManager.getInstance().getParameterWindowMode(),windowMode);
			add(parameters,UIManager.getInstance().getParameterReportIdentifier(),reportIdentifier);
			add(parameters,UIManager.getInstance().getParameterViewIdentifier(),UIManager.getInstance().getViewIdentifierDynamicReport());
			add(parameters,UIManager.getInstance().getParameterPrint(),Boolean.TRUE.equals(print));
		}
	}
	
}
