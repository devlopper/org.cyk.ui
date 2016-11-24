package org.cyk.ui.api.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness.FindDoSomethingTextParameters;
import org.cyk.system.root.business.api.language.LanguageBusiness.FindTextResult;
import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.CommonBusinessAction;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.ui.api.CascadeStyleSheet;
import org.cyk.ui.api.Icon;
import org.cyk.ui.api.IdentifierProvider;
import org.cyk.ui.api.MessageManager;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.UIMessageManager;
import org.cyk.ui.api.UIProvider;
import org.cyk.utility.common.AbstractBuilder;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.ListenerUtils;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstractCommandable implements UICommandable , Serializable {

	private static final long serialVersionUID = 3245517653342272298L;

	@Setter protected UIMessageManager messageManager;
	@Getter @Setter protected UICommand command;
	@Getter @Setter protected BusinessEntityInfos businessEntityInfos;
	@Getter protected String identifier,label;
	@Getter @Setter protected String tooltip,onClick;
	@Getter @Setter protected Integer index;
	@Getter protected Icon icon;
	@Getter protected CascadeStyleSheet cascadeStyleSheet = new CascadeStyleSheet();
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
	public UICommandable addCommandListener(CommandListener commandListener) {
		this.command.getCommandListeners().add(commandListener);
		return this;
	}
	
	@Override
	public UICommandable setIdentifier(String anIdentifier) {
		this.identifier = anIdentifier;
		return this;
	}
	
	@Override
	public UICommandable setLabel(String aLabel) {
		this.label = aLabel;
		return this;
	}
	
	@Override
	public UICommandable setIcon(Icon anIcon) {
		this.icon = anIcon;
		return this;
	}
	
	@Override
	public UICommandable setCascadeStyleSheet(CascadeStyleSheet cascadeStyleSheet) {
		this.cascadeStyleSheet = cascadeStyleSheet;
		return this;
	}
	
	@Override
	public Boolean getIsNavigationCommand() {
		return CommandRequestType.UI_VIEW.equals(commandRequestType);
	}
	
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
		Parameter.add(parameters,UniformResourceLocatorParameter.CLASS, UIManager.getInstance().businessEntityInfos(identifiableClass).getIdentifier());
		setViewType(ViewType.DYNAMIC_CRUD_ONE);
		return this;
	}
	
	@Override
	public UICommandable addReadAllParameters(Class<? extends AbstractIdentifiable> identifiableClass) {
		Parameter.add(parameters,UniformResourceLocatorParameter.CLASS, UIManager.getInstance().businessEntityInfos(identifiableClass).getIdentifier());
		setViewType(ViewType.DYNAMIC_CRUD_MANY);
		return this;
	}
	
	public UICommandable addActionParameter(String actionIdentifier) {
		Parameter.add(parameters, UniformResourceLocatorParameter.ACTION_IDENTIFIER,actionIdentifier);
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

		private FindDoSomethingTextParameters labelParameters;
		private CommonBusinessAction commonBusinessAction;
		private String actionIdentifier,selectedTabId;
		private Class<? extends AbstractIdentifiable> identifiableClass;
		private Boolean one;
		private AbstractIdentifiable master;
		private FindTextResult findTextResult;
		
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
			findTextResult = new FindTextResult(labelId, inject(LanguageBusiness.class).findText(labelId));
			setLabel(findTextResult.getValue());
			/*instance.setIdentifier(findTextResult.getIdentifier());
			if(instance.cascadeStyleSheet!=null && StringUtils.isNotBlank(instance.label))
				instance.cascadeStyleSheet.addClass(CascadeStyleSheet.generateUniqueClassFrom(instance,findTextResult)); //TODO many call of setLabel will add too more classes where previous are useless
			*/return this;
		}
		public Builder<COMMANDABLE> setSelectedTabId(String selectedTabId){
			this.selectedTabId = selectedTabId;
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
		
		public Builder<COMMANDABLE> setLabelParameters(FindDoSomethingTextParameters labelParameters){
			this.labelParameters = labelParameters;
			return this;
		}
		
		public Builder<COMMANDABLE> setCommonBusinessAction(Object action){
			if(action instanceof Crud)
				this.commonBusinessAction = CommonBusinessAction.valueOf( ((Crud)action).name());
			else if(action instanceof CommonBusinessAction)
				this.commonBusinessAction = (CommonBusinessAction) action;
			else
				this.commonBusinessAction = null;
			return this;
		}
		
		public Builder<COMMANDABLE> setActionIdentifier(String actionIdentifier){
			this.actionIdentifier = actionIdentifier;
			return this;
		}
		
		public Builder<COMMANDABLE> setIdentifiableClass(Class<? extends AbstractIdentifiable> identifiableClass){
			this.identifiableClass = identifiableClass;
			instance.setBusinessEntityInfos(UIManager.getInstance().businessEntityInfos(identifiableClass));
			return this;
		}
		
		@SuppressWarnings("unchecked")
		public Builder<COMMANDABLE> setBusinessEntityInfos(BusinessEntityInfos businessEntityInfos){
			this.identifiableClass = (Class<? extends AbstractIdentifiable>) businessEntityInfos.getClazz();
			instance.setBusinessEntityInfos(businessEntityInfos);
			return this;
		}
		
		public Builder<COMMANDABLE> setOne(Boolean one){
			this.one = one;
			return this;
		}
		
		public Builder<COMMANDABLE> setMaster(AbstractIdentifiable master){
			this.master = master;
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
		
		public Builder<COMMANDABLE> addPreviousViewParameter(String previousView){
			Parameter.addPreviousViewParameter(instance.parameters,previousView);
			return this;
		}
		public Builder<COMMANDABLE> addPreviousViewParameter(){
			return addPreviousViewParameter(UIManager.getInstance().getCurrentViewUrl());
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
					.setView(StringUtils.isBlank(view)?UIManager.getInstance().getViewIdentifier(identifiable, CommonBusinessAction.valueOf(crud.name())):view)
					.addCrudParameters(crud, identifiable)
					.addPreviousViewParameter()
					.create();
		}
		public static UICommandable createCrud(Crud crud,AbstractIdentifiable identifiable,String labelId,Icon icon){
			return createCrud(crud,identifiable, labelId, icon,null);
		}
		
		public static UICommandable createConsult(AbstractIdentifiable identifiable,Icon icon,String view){
			UICommandable commandable = createCrud(Crud.READ, identifiable, RootBusinessLayer.getInstance().getFormatterBusiness().format(identifiable), icon, view);
			commandable.setLabel(RootBusinessLayer.getInstance().getFormatterBusiness().format(identifiable));
			return commandable;
		}
		public static UICommandable createConsult(AbstractIdentifiable identifiable,Icon icon){
			return createConsult(identifiable, icon, null);
		}
		
		public static UICommandable createCreate(AbstractIdentifiable master,Class<? extends AbstractIdentifiable> identifiableClass,String labelId,Icon icon){	
			return instanciateOne().setLabelFromId(labelId).setIcon(icon)
					.setCommonBusinessAction(Crud.CREATE).setOne(Boolean.TRUE).setIdentifiableClass(identifiableClass).setMaster(master)
					//.setView(UIManager.getInstance().getViewIdentifier(identifiableClass, Crud.CREATE,Boolean.TRUE))
					//.addParameters(master)
					//.addCreateOneParameters(identifiableClass)
					.create();	
		}
		public static UICommandable createCreate(Class<? extends AbstractIdentifiable> identifiableClass,String labelId,Icon icon){
			return createCreate(null, identifiableClass, labelId, icon);
		}
		@SuppressWarnings("unchecked")
		public static UICommandable createCreate(BusinessEntityInfos businessEntityInfos,Icon icon){
			return createCreate((Class<? extends AbstractIdentifiable>) businessEntityInfos.getClazz(), null, icon);
		}
		public static UICommandable createCreate(BusinessEntityInfos businessEntityInfos){
			return createCreate(businessEntityInfos, Icon.ACTION_ADD);
		}
		public static UICommandable createCreate(Class<? extends AbstractIdentifiable> identifiableClass){
			return createCreate(UIManager.getInstance().businessEntityInfos(identifiableClass), Icon.ACTION_ADD);
		}
		
		public static UICommandable createUpdate(AbstractIdentifiable identifiable,Boolean addIdentifiableText){
			UICommandable commandable = createCrud(Crud.UPDATE,identifiable, "command.edit", Icon.ACTION_EDIT);
			commandable.setLabel(commandable.getLabel()
					+(Boolean.TRUE.equals(addIdentifiableText) ?Constant.CHARACTER_SPACE+RootBusinessLayer.getInstance().getFormatterBusiness().format(identifiable) : Constant.EMPTY_STRING )
					);
			return commandable;
		}
		public static UICommandable createUpdate(AbstractIdentifiable identifiable){
			return createUpdate(identifiable, Boolean.FALSE);
		}
		public static UICommandable createUpdateGlobalIdentifier(AbstractIdentifiable identifiable){
			UICommandable commandable = createCrud(Crud.UPDATE, identifiable, "command.edit", Icon.ACTION_EDIT, ListenerUtils.getInstance()
					.getString(IdentifierProvider.COLLECTION, new ListenerUtils.StringMethod<IdentifierProvider>() {
						@Override
						public String execute(IdentifierProvider identifierProvider) {
							return identifierProvider.getViewGlobalIdentifierEdit();
						}
			}));
			
			return commandable;
		}
		
		public static UICommandable createDelete(AbstractIdentifiable identifiable,Boolean addIdentifiableText){
			UICommandable commandable = createCrud(Crud.DELETE,identifiable, "command.delete", Icon.ACTION_DELETE);
			commandable.setLabel(commandable.getLabel()
					+(Boolean.TRUE.equals(addIdentifiableText) ?Constant.CHARACTER_SPACE+RootBusinessLayer.getInstance().getFormatterBusiness().format(identifiable) : Constant.EMPTY_STRING )
					);
			return commandable;
		}
		public static UICommandable createDelete(AbstractIdentifiable identifiable){
			return createUpdate(identifiable, Boolean.FALSE);
		}
		
		public static UICommandable createReport(AbstractIdentifiable identifiable,String reportIdentifier,String labelId,Icon icon,Boolean popup){
			return instanciateOne().setLabelFromId(labelId).setIcon(icon)
					.setView(ViewType.TOOLS_REPORT)
					.addReportParameters(identifiable, reportIdentifier, "pdf", Boolean.TRUE, null)
					.create();	
		}		
		public static UICommandable createReport(AbstractIdentifiable identifiable,String reportIdentifier,String labelid,Icon iconType){
			return createReport(identifiable, reportIdentifier, labelid, iconType, Boolean.TRUE);
		}
		
		private UICommandable crud(BusinessEntityInfos businessEntityInfos,ViewType viewType,Icon icon){
			UICommandable commandable = Builder.instanciateOne().setLabelFromId(businessEntityInfos.getUserInterface().getLabelId()).setIcon(icon).create();
			commandable.setBusinessEntityInfos(businessEntityInfos);
			commandable.setViewType(viewType);
			return commandable;
		}
		
		@SuppressWarnings("unchecked")
		public UICommandable crudOne(BusinessEntityInfos businessEntityInfos,Icon icon){
			UICommandable c = crud(businessEntityInfos,null, icon);
			FindDoSomethingTextParameters parameters = new FindDoSomethingTextParameters();
			parameters.setActionIdentifier(CommonBusinessAction.CREATE);
			parameters.getSubjectClassLabelTextParameters().setClazz((Class<? extends AbstractIdentifiable>) businessEntityInfos.getClazz());
			parameters.setVerb(Boolean.TRUE);
			c.setLabel(inject(LanguageBusiness.class).findDoSomethingText(parameters).getValue());
			if(StringUtils.isEmpty(businessEntityInfos.getUserInterface().getEditViewId()))
				c.setViewType(ViewType.DYNAMIC_CRUD_ONE);
			else{
				c.setViewId(businessEntityInfos.getUserInterface().getEditViewId());
				c.getParameters().add(new Parameter(UniformResourceLocatorParameter.CLASS, UIManager.getInstance().keyFromClass(businessEntityInfos)));
				c.getParameters().add(new Parameter(UniformResourceLocatorParameter.CRUD, UniformResourceLocatorParameter.CRUD_CREATE));
			}
			logTrace("Crud one view ID of {} is {}", businessEntityInfos.getClazz().getSimpleName(),c.getViewType()==null?c.getViewId():c.getViewType());
			return c;
		}
		
		public UICommandable crudOne(Class<? extends AbstractIdentifiable> aClass,Icon icon){
			return crudOne(UIManager.getInstance().businessEntityInfos(aClass), icon);
		}
		
		public static UICommandable createList(BusinessEntityInfos businessEntityInfos,Icon icon){
			UICommandable commandable = instanciateOne().setIcon(icon).setCommonBusinessAction(CommonBusinessAction.LIST)
					.setBusinessEntityInfos(businessEntityInfos)
					.create();	
			/*
			UICommandable c = crud(businessEntityInfos, null, icon);
			//c.setLabel(UIManager.getInstance().getLanguageBusiness().findText("list.of",
			//		new Object[]{UIManager.getInstance().getLanguageBusiness().findText(businessEntityInfos.getUiLabelId())}));
			if(StringUtils.isEmpty(businessEntityInfos.getUserInterface().getListViewId()))
				c.setViewType(ViewType.DYNAMIC_CRUD_MANY);
			else{
				c.setViewId(businessEntityInfos.getUserInterface().getListViewId());
				c.setCommandRequestType(CommandRequestType.UI_VIEW);
				c.getParameters().add(new Parameter(UIManager.getInstance().getClassParameter(), UIManager.getInstance().keyFromClass(businessEntityInfos)));
			}
			return c;
			*/
			return commandable;
		}
		
		public static UICommandable createList(Class<? extends AbstractIdentifiable> aClass,Icon icon){
			return createList(UIManager.getInstance().businessEntityInfos(aClass), icon);
		}
		
		public UICommandable crudMenu(Class<? extends AbstractIdentifiable> aClass){
			UICommandable commandable,p;
			BusinessEntityInfos businessEntityInfos = UIManager.getInstance().businessEntityInfos(aClass);
			commandable = Builder.instanciateOne().setLabelFromId(businessEntityInfos.getUserInterface().getLabelId()).create();
			commandable.getChildren().add(p=crudOne(aClass, Icon.ACTION_ADD));
			p.setLabel(UIManager.getInstance().text("command.item.add"));
			//commandable.getChildren().add(p=crudMany(aClass, Icon.THING_LIST));
			p.setLabel(UIManager.getInstance().text("command.list"));
			
			return commandable;
		}
		
		public static UICommandable createCreateMany(BusinessEntityInfos businessEntityInfos,Icon icon){
			UICommandable commandable = instanciateOne().setIcon(icon).setCommonBusinessAction(CommonBusinessAction.CREATE).setOne(Boolean.FALSE)
					.setBusinessEntityInfos(businessEntityInfos)
					.setLabel(inject(LanguageBusiness.class).findText("command.createmany"+businessEntityInfos.getVarName().toLowerCase()))
					.create();	
			
			//System.out.println("ViewId : "+commandable.getViewId());
			
			return commandable;
			/*
			UICommandable c = crud(businessEntityInfos,null, icon);
			c.setLabel(inject(LanguageBusiness.class).findText("command.createmany"+businessEntityInfos.getVarName().toLowerCase()));
			if(StringUtils.isEmpty(businessEntityInfos.getUserInterface().getCreateManyViewId()))
				;
			else{
				c.setViewId(businessEntityInfos.getUserInterface().getCreateManyViewId());
				c.getParameters().add(new Parameter(UIManager.getInstance().getClassParameter(), UIManager.getInstance().keyFromClass(businessEntityInfos)));
				c.getParameters().add(new Parameter(UIManager.getInstance().getCrudParameter(), UIManager.getInstance().getCrudCreateParameter()));
			}
			logTrace("Create many view ID of {} is {}", businessEntityInfos.getClazz().getSimpleName(),c.getViewType()==null?c.getViewId():c.getViewType());
			return c;
			*/
		}
		public static UICommandable createCreateMany(Class<? extends AbstractIdentifiable> aClass,Icon icon){
			return createCreateMany(UIManager.getInstance().businessEntityInfos(aClass), icon);
		}
		
		
		public static UICommandable createSelectOne(Class<? extends AbstractIdentifiable> aClass,String actionIdentifier,Icon icon){
			return createSelectOne(UIManager.getInstance().businessEntityInfos(aClass),actionIdentifier, icon);
		}
		@SuppressWarnings("unchecked")
		public static UICommandable createSelectOne(BusinessEntityInfos businessEntityInfos,String actionIdentifier,Icon icon){
			return instanciateOne().setIcon(icon).setCommonBusinessAction(CommonBusinessAction.SELECT).setOne(Boolean.TRUE).setActionIdentifier(actionIdentifier)
					.setIdentifiableClass((Class<? extends AbstractIdentifiable>) businessEntityInfos.getClazz())
					.create();	
		}
		
		public static UICommandable createSelectMany(Class<? extends AbstractIdentifiable> aClass,String actionIdentifier,Icon icon){
			return createSelectMany(UIManager.getInstance().businessEntityInfos(aClass),actionIdentifier, icon);
		}
		@SuppressWarnings("unchecked")
		public static UICommandable createSelectMany(BusinessEntityInfos businessEntityInfos,String actionIdentifier,Icon icon){
			return instanciateOne().setIcon(icon).setCommonBusinessAction(CommonBusinessAction.SELECT).setOne(Boolean.FALSE).setActionIdentifier(actionIdentifier)
					.setIdentifiableClass((Class<? extends AbstractIdentifiable>) businessEntityInfos.getClazz())
					.create();	
		}
		
		/**/
		
		/**/
		
		@Override
		public COMMANDABLE build() {
			if(instance.getCommand()==null)
				instance.setCommand(new DefaultCommand());
			
			if(instance.getCommand().getMessageManager()==null)
				instance.getCommand().setMessageManager(MessageManager.INSTANCE);
			
			if(instance.getViewId()==null && instance.getViewType()==null){
				setView(UIManager.getInstance().getViewIdentifier(identifiableClass, commonBusinessAction,one));
				if(instance.getViewId()==null)//No specific view has been defined so we'll use the dynamic one
					if(Boolean.TRUE.equals(one))
						setView(ViewType.DYNAMIC_CRUD_ONE);
					else
						setView(ViewType.DYNAMIC_CRUD_MANY);
					//setView(UIManager.getInstance().getViewDynamic(commonBusinessAction,one));
			}
			if(instance.getViewId() != null || instance.getViewType()!=null)
				instance.setCommandRequestType(CommandRequestType.UI_VIEW);
			
			if(StringUtils.isBlank(instance.getLabel()))
				if(StringUtils.isBlank(actionIdentifier))
					setLabelParameters(labelParameters = FindDoSomethingTextParameters.create(commonBusinessAction, identifiableClass));
				else{
					findTextResult = inject(LanguageBusiness.class).findActionIdentifierText(actionIdentifier, instance.getBusinessEntityInfos(), Boolean.TRUE);					
					setLabel(findTextResult.getValue());
				}
			//setLabelParameters(labelParameters = FindDoSomethingTextParameters.create(actionIdentifier, identifiableClass));
			
			if(StringUtils.isBlank(instance.getLabel()) && labelParameters!=null){
				findTextResult = inject(LanguageBusiness.class).findDoSomethingText(labelParameters);
				instance.setLabel(findTextResult.getValue());
			}
			
			if(master!=null)
				addParameters(master);
			
			if(StringUtils.isNotBlank(actionIdentifier))
				Parameter.add(instance.getParameters(),UniformResourceLocatorParameter.ACTION_IDENTIFIER, actionIdentifier);
			
			if(CommonBusinessAction.CREATE.equals(commonBusinessAction)){
				addCreateOneParameters(identifiableClass);
			}else if(CommonBusinessAction.SELECT.equals(commonBusinessAction)){
				Parameter.add(instance.getParameters(),UniformResourceLocatorParameter.CLASS, UIManager.getInstance().keyFromClass(instance.getBusinessEntityInfos()));
			}else if(CommonBusinessAction.LIST.equals(commonBusinessAction)){
				Parameter.add(instance.getParameters(),UniformResourceLocatorParameter.CLASS, UIManager.getInstance().keyFromClass(instance.getBusinessEntityInfos()));
			}
			
			if(StringUtils.isEmpty(selectedTabId))
				;
			else
				instance.addParameter(UniformResourceLocatorParameter.TAB_ID, selectedTabId);
			
			if(findTextResult!=null){
				instance.setIdentifier(findTextResult.getIdentifier());
				if(instance.cascadeStyleSheet!=null && StringUtils.isNotBlank(instance.label))
					instance.cascadeStyleSheet.addClass(CascadeStyleSheet.generateUniqueClassFrom(instance,findTextResult)); //TODO many call of setLabel will add too more classes where previous are useless
			}
			return instance;
		}
		
		/**/

		@SuppressWarnings("unchecked")
		public COMMANDABLE create(){
			return (COMMANDABLE) UIProvider.getInstance().createCommandable(this);
		}
		
		/**/
		
		public static AbstractCommandable create(CommandListener commandListener, String labelId, Icon icon,Object view, EventListener eventListener, ProcessGroup aProcessGroup) {
			return Builder.instanciateOne().setCommandListener(commandListener).setEventListener(eventListener).setProcessGroup(aProcessGroup).setLabelFromId(labelId)
					.setIcon(icon).setView(view).create();
		}
		
		public static AbstractCommandable create(CommandListener commandListener, String labelId,Icon icon,Object view){
			return create(commandListener,labelId,icon,view,null,null);
		}
		
		public static AbstractCommandable create(String labelId,Icon icon,Object view){
			return create(null,labelId,icon,view);
		}
		
		public static AbstractCommandable create(String labelId,Icon icon){
			return create(labelId, icon, null);
		}
		
	}
}
