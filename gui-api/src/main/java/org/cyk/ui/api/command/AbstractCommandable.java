package org.cyk.ui.api.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.Icon;
import org.cyk.ui.api.MessageManager;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.UIMessageManager;
import org.cyk.ui.api.UIProvider;
import org.cyk.utility.common.AbstractBuilder;

public abstract class AbstractCommandable implements UICommandable , Serializable {

	private static final long serialVersionUID = 3245517653342272298L;

	@Setter protected UIMessageManager messageManager;
	@Getter @Setter protected UICommand command;
	@Getter @Setter protected BusinessEntityInfos businessEntityInfos;
	@Getter @Setter protected String identifier,label,tooltip,onClick;
	@Getter @Setter protected Integer index;
	@Getter @Setter protected Icon icon;
	@Getter @Setter protected Boolean showLabel=Boolean.TRUE,rendered=Boolean.TRUE,requested=Boolean.FALSE;
	@Getter @Setter protected Object viewId;
	@Getter @Setter protected ViewType viewType;
	@Getter @Setter protected NavigationMode navigationMode = NavigationMode.DEFAULT;
	@Getter @Setter protected Class<?> dynamicClass; 
	@Getter @Setter protected EventListener eventListener;
	@Getter @Setter protected RenderType renderType;
	@Getter @Setter protected ProcessGroup processGroup;
	@Getter @Setter protected CommandRequestType commandRequestType;
	@Getter @Setter protected Collection<UICommandable> children = new ArrayList<UICommandable>(){
		private static final long serialVersionUID = -5378067672438543808L;
		public boolean add(UICommandable aCommandable){
			if(StringUtils.isBlank(aCommandable.getIdentifier()))
				aCommandable.setIdentifier(RandomStringUtils.randomAlphabetic(4));
			return super.add(aCommandable);
		}
	};
	
	@Getter @Setter protected Collection<Parameter> parameters = new ArrayList<>();
	
	@Override
	public Boolean getIsNavigationCommand() {
		return CommandRequestType.UI_VIEW.equals(commandRequestType);
	}
	
	/*@Override
	public UICommandable addChild(String labelId, IconType iconType,String viewId,Collection<Parameter> parameters) {
		UICommandable child = UIProvider.getInstance().createCommandable(labelId, iconType);
		child.setViewId(viewId);
		child.setCommandRequestType(CommandRequestType.UI_VIEW);
		if(parameters != null)
			child.getParameters().addAll(parameters);
		addChild(child);
		return child;
	}
	
	@Override
	public UICommandable addChild(String labelId, IconType iconType,ViewType viewType,Collection<Parameter> parameters) {
		UICommandable child = UIProvider.getInstance().createCommandable(labelId, iconType);
		child.setViewType(viewType);
		child.setCommandRequestType(CommandRequestType.UI_VIEW);
		if(parameters != null)
			child.getParameters().addAll(parameters);
		addChild(child);
		return child;
	}*/
	
	@Override
	public void addChild(UICommandable aCommandable) {
		children.add(aCommandable);
	}
	
	@Override
	public Parameter getParameter(String name) {
		return Parameter.get(parameters, name);
	}
	
	@Override
	public UICommandable addParameter(String name, Object value) {
		Parameter.add(parameters, name, value);
		return this;
	}
	
	@Override
	public UICommandable addParameter(AbstractIdentifiable identifiable) {
		Parameter.add(parameters,UIManager.getInstance().businessEntityInfos(identifiable.getClass()).getIdentifier(), identifiable.getIdentifier());
		return this;
	}
	
	@Override
	public UICommandable addCrudParameters(Crud crud,AbstractIdentifiable identifiable, AbstractOutputDetails<?> details) {
		Parameter.addCrud(parameters, crud, identifiable, details);
		return this;
	}
	
	@Override
	public UICommandable addCrudParameters(Crud crud,AbstractIdentifiable identifiable) {
		Parameter.addCrud(parameters, crud, identifiable);
		return this;
	}
	
	@Override
	public UICommandable addCreateParameters(Class<? extends AbstractIdentifiable> identifiableClass) {
		Parameter.add(parameters,UIManager.getInstance().getClassParameter(), UIManager.getInstance().businessEntityInfos(identifiableClass).getIdentifier());
		setViewType(ViewType.DYNAMIC_CRUD_ONE);
		return this;
	}
	
	@Override
	public UICommandable addReadAllParameters(Class<? extends AbstractIdentifiable> identifiableClass) {
		Parameter.add(parameters,UIManager.getInstance().getClassParameter(), UIManager.getInstance().businessEntityInfos(identifiableClass).getIdentifier());
		setViewType(ViewType.DYNAMIC_CRUD_MANY);
		return this;
	}
	@Override
	public UICommandable addPreviousViewParameter() {
		return this;
	}
	@Override
	public UICommandable addDefaultParameters() {
		return this;
	}
	
	@Override
	public UICommandable setParameter(String name, Object value) {
		Parameter.setParameter(parameters, name, value);
		return this;
	}
	
	@Override
	public String toString() {
		return index+","+identifier+","+label;
	}
	
	/**/
	
	public static class Builder<COMMANDABLE extends AbstractCommandable> extends AbstractBuilder<COMMANDABLE> implements Serializable {
		private static final long serialVersionUID = 7285848895016815525L;

		public Builder(Class<COMMANDABLE> commandableClass) {
			super(commandableClass);
		}
		
		@SuppressWarnings("unchecked")
		public static <COMMANDABLE extends AbstractCommandable> Builder<COMMANDABLE> instanciateOne(){
			Builder<COMMANDABLE> builder = new Builder<COMMANDABLE>((Class<COMMANDABLE>) UIProvider.getInstance().getCommandableClass());
			builder.instanciate();
			return builder;
		}
		public Builder<COMMANDABLE> setLabel(String label){
			instance.setLabel(label);
			return this;
		}
		public Builder<COMMANDABLE> setLabelFromId(String labelId){
			instance.setLabel(RootBusinessLayer.getInstance().getLanguageBusiness().findText(labelId));
			return this;
		}
		public Builder<COMMANDABLE> setIcon(Icon icon){
			instance.setIcon(icon);
			return this;
		}
		public Builder<COMMANDABLE> setCommandListeners(CommandListener...commandListeners){
			if(commandListeners!=null)
				for(CommandListener commandListener : commandListeners){
					if(instance.getCommand()==null)
						instance.setCommand(new DefaultCommand());
					instance.getCommand().getCommandListeners().add(commandListener);
				}
			return this;
		}
		public Builder<COMMANDABLE> setCommandListener(CommandListener commandListener){
			return setCommandListeners(new CommandListener[]{commandListener});
		}
		
		public Builder<COMMANDABLE> setIdentifier(String identifier){
			instance.setIdentifier(identifier);
			return this;
		}
		
		public Builder<COMMANDABLE> setShowLabel(Boolean value){
			instance.setShowLabel(value);
			return this;
		}
		
		public Builder<COMMANDABLE> setRendered(Boolean value){
			instance.setRendered(value);
			return this;
		}
		
		public Builder<COMMANDABLE> setView(Object view){
			if(view instanceof ViewType)
				instance.setViewType((ViewType) view);
			else
				instance.setViewId(view);
			return this;
		}
		
		public Builder<COMMANDABLE> addDefaultParameters(){
			instance.addDefaultParameters();
			return this;
		}
		
		public Builder<COMMANDABLE> setParameters(Collection<Parameter> parameters){
			instance.getParameters().addAll(parameters);
			return this;
		}
		
		public Builder<COMMANDABLE> setEventListener(EventListener eventListener){
			instance.setEventListener(eventListener);
			return this;
		}
		
		public Builder<COMMANDABLE> setProcessGroup(ProcessGroup processGroup){
			instance.setProcessGroup(processGroup);
			return this;
		}
		
		/* Parameters */
		public Builder<COMMANDABLE> addParameter(String name,Object value){
			Parameter.add(instance.parameters, name, value);
			return this;
		}
		
		public Builder<COMMANDABLE> addCrudParameters(Crud crud,AbstractIdentifiable identifiable){
			Parameter.addCrud(instance.parameters, crud, identifiable);
			return this;
		}
		
		public Builder<COMMANDABLE> addCreateOneParameters(Class<? extends AbstractIdentifiable> identifiableClass){
			Parameter.addCreateOne(instance.parameters, identifiableClass);
			return this;
		}
		
		public Builder<COMMANDABLE> addParameters(AbstractIdentifiable identifiable){
			Parameter.add(instance.parameters, identifiable);
			return this;
		}
		
		public Builder<COMMANDABLE> addReportParameters(AbstractIdentifiable anIdentifiable,String reportIdentifier,String fileExtension,Boolean print,String windowMode){
			Parameter.addReport(instance.parameters, anIdentifiable,reportIdentifier,fileExtension,print,windowMode);
			return this;
		}
		
		/**/
		
		public static UICommandable createCrud(Crud crud,AbstractIdentifiable identifiable,String labelId,Icon icon,String view){
			return instanciateOne().setLabelFromId(labelId).setIcon(icon)
					.setView(StringUtils.isBlank(view)?UIManager.getInstance().getViewIdentifier(identifiable, crud):view)
					.addCrudParameters(crud, identifiable)
					.create();
		}
		public static UICommandable createCrud(Crud crud,AbstractIdentifiable identifiable,String labelId,Icon icon){
			return createCrud(crud,identifiable, labelId, icon,null);
		}
		
		public static UICommandable createConsult(AbstractIdentifiable identifiable,Icon icon,String view){
			return createCrud(Crud.READ, identifiable, RootBusinessLayer.getInstance().getFormatterBusiness().format(identifiable), icon, view);
		}
		public static UICommandable createConsult(AbstractIdentifiable identifiable,Icon icon){
			return createConsult(identifiable, icon, null);
		}
		/*
		public UICommandable createDeleteCommandable(AbstractIdentifiable identifiable,String labelid,Icon iconType,String viewId){
			UICommandable commandable = UIProvider.getInstance().createCommandable(labelid, iconType);
			commandable.setViewId(StringUtils.isBlank(viewId)?editOneOutcome(identifiable.getClass()):viewId);
			commandable.setCommandRequestType(CommandRequestType.UI_VIEW);
			commandable.addCrudParameters(UIManager.getInstance().getCrudDeleteParameter(), identifiable);
			return commandable;
		}
		public UICommandable createDeleteCommandable(AbstractIdentifiable identifiable,String labelid,Icon iconType){
			return createDeleteCommandable(identifiable, labelid, iconType,null);
		}
		
		public UICommandable createConsultCommandable(AbstractIdentifiable identifiable,String labelid,Icon iconType){
			UICommandable commandable = UIProvider.getInstance().createCommandable(labelid, iconType);
			commandable.setViewId(consultOneOutcome(identifiable.getClass()));
			commandable.setCommandRequestType(CommandRequestType.UI_VIEW);
			commandable.addCrudParameters(UIManager.getInstance().getCrudReadParameter(), identifiable);
			return commandable;
		}
		public UICommandable createConsultCommandable(AbstractIdentifiable identifiable,Icon iconType){
			UICommandable commandable = createConsultCommandable(identifiable, "button", iconType);
			commandable.setLabel(RootBusinessLayer.getInstance().getFormatterBusiness().format(identifiable));
			return commandable;
		}
		*/
		public static UICommandable createCreate(AbstractIdentifiable master,Class<? extends AbstractIdentifiable> identifiableClass,String labelId,Icon icon){
			return instanciateOne().setLabelFromId(labelId).setIcon(icon)
					.setView(UIManager.getInstance().getViewIdentifier(identifiableClass, Crud.CREATE,Boolean.TRUE))
					.addParameters(master)
					.addCreateOneParameters(identifiableClass)
					.create();	
		}
		
		public static UICommandable createReport(AbstractIdentifiable identifiable,String reportIdentifier,String labelId,Icon icon,Boolean popup){
			return instanciateOne().setLabelFromId(labelId).setIcon(icon)
					.setView(ViewType.TOOLS_REPORT)
					.addReportParameters(identifiable, reportIdentifier, "pdf", Boolean.TRUE, null)
					.create();	
			/*
			UICommandable commandable = UIProvider.getInstance().createCommandable(labelid, iconType);
			commandable.setCommandRequestType(CommandRequestType.UI_VIEW);
			if(Boolean.TRUE.equals(popup)){
				commandable.setCommandRequestType(CommandRequestType.UI_VIEW);
				commandable.setOnClick(JavaScriptHelper.getInstance().openWindow(identifiable.getIdentifier().toString(), 
					reportUrl(identifiable, reportIdentifier, "pdf", Boolean.FALSE), 300, 300));
			}else{
				commandable.setViewType(ViewType.TOOLS_REPORT);
				commandable.getParameters().addAll(reportParameters(identifiable, reportIdentifier,Boolean.FALSE));
			}
			return commandable;
			*/
		}
		
		public static UICommandable createReport(AbstractIdentifiable identifiable,String reportIdentifier,String labelid,Icon iconType){
			return createReport(identifiable, reportIdentifier, labelid, iconType, Boolean.TRUE);
		}
		
		/**/
		
		/**/
		
		@Override
		public COMMANDABLE build() {
			if(instance.getCommand()==null)
				instance.setCommand(new DefaultCommand());
			
			if(instance.getCommand().getMessageManager()==null)
				instance.getCommand().setMessageManager(MessageManager.INSTANCE);
			
			if(instance.getViewId() != null)
				instance.setCommandRequestType(CommandRequestType.UI_VIEW);
			return instance;
		}
		
		/**/

		@SuppressWarnings("unchecked")
		public COMMANDABLE create(){
			return (COMMANDABLE) UIProvider.getInstance().createCommandable(this);
		}
		
		@SuppressWarnings("unchecked")
		public COMMANDABLE create(String labelId,Icon icon,Object view){
			return (COMMANDABLE) instanciateOne().setLabelFromId(labelId).setIcon(icon).setView(view)
				.create();
		}
		
		public COMMANDABLE create(String labelId,Icon icon){
			return create(labelId, icon, null);
		}
	}
}
