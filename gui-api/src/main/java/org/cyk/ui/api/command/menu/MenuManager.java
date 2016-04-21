package org.cyk.ui.api.command.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.CommonBusinessAction;
import org.cyk.system.root.business.api.language.LanguageBusiness.FindDoSomethingTextParameters;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.system.root.model.security.License;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.ui.api.AbstractApplicationUIManager;
import org.cyk.ui.api.AbstractUserSession;
import org.cyk.ui.api.Icon;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.UserDeviceType;
import org.cyk.ui.api.command.AbstractCommandable.Builder;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.CommandRequestType;
import org.cyk.ui.api.command.UICommandable.Parameter;
import org.cyk.ui.api.command.UICommandable.ViewType;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.cdi.AbstractBean;

@Singleton @Deployment(initialisationType=InitialisationType.EAGER)
public class MenuManager extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 4331240830505008164L;
	public static final String COMMANDABLE_NOTIFICATIONS_IDENTIFIER = "notifications";
	public static final String COMMANDABLE_EVENT_CALENDAR_IDENTIFIER = "eventcalendar";
	public static final String COMMANDABLE_USER_ACCOUNT_IDENTIFIER = "useraccount";
	public static enum ModuleGroup{HOME,TOOLS,CONTROL_PANEL,REPORT,USER_ACCOUNT,HELP}
	
	private static MenuManager INSTANCE;
	public static MenuManager getInstance() {
		return INSTANCE;
	}
	
	@Inject private ApplicationBusiness applicationBusiness;
	
	private Map<ModuleGroup, UICommandable> groupsMap = new HashMap<>();
	
	@Getter private Collection<MenuListener/*<AbstractUserSession<?, ?>>*/> menuListeners = new ArrayList<>();
	
	@Getter @Setter private Boolean autoGenerateReferenceEntityMenu=Boolean.FALSE;
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	private UICommandable homeCommandable(){
		UICommandable commandable = Builder.instanciateOne().setLabelFromId("command.home").setView(ViewType.HOME).setIcon(Icon.THING_HOME).build();
		commandable.setCommandRequestType(CommandRequestType.UI_VIEW);
		return commandable;
	}
	
	private UICommandable logoutCommandable(){
		UICommandable commandable = Builder.instanciateOne().setLabelFromId("command.useraccount.logout").setView(ViewType.USERACCOUNT_LOGOUT).setIcon(Icon.ACTION_LOGOUT).build();
		commandable.setCommandRequestType(CommandRequestType.BUSINESS_PROCESSING);
		return commandable;
	}
	
	private UICommandable notificationsCommandable(){
		UICommandable commandable = Builder.instanciateOne().setLabelFromId("command.notifications").setView(ViewType.NOTIFICATIONS).setIcon(Icon.THING_NOTIFICATIONS).build();
		commandable.setCommandRequestType(CommandRequestType.UI_VIEW);
		commandable.setIdentifier(COMMANDABLE_NOTIFICATIONS_IDENTIFIER);
		return commandable;
	}
	
	private UICommandable agendaCommandable(){
		UICommandable commandable = Builder.instanciateOne().setLabelFromId("command.agenda").setView(ViewType.TOOLS_AGENDA).setIcon(Icon.THING_CALENDAR).build();
		commandable.setCommandRequestType(CommandRequestType.UI_VIEW);
		commandable.setIdentifier(COMMANDABLE_EVENT_CALENDAR_IDENTIFIER);
		return commandable;
	}
	
	public UICommandable createModuleGroup(AbstractUserSession<?,?> userSession,ModuleGroup moduleGroup) {
		Boolean moduleGroupCreateable = Boolean.TRUE;
		for(MenuListener<AbstractUserSession<?, ?>> listener : menuListeners){
			Boolean v = listener.moduleGroupCreateable(userSession,moduleGroup);
			if(v!=null)
				moduleGroupCreateable = v;
		}
		if(Boolean.FALSE.equals(moduleGroupCreateable))
			return null;
		UICommandable commandableGroup = null,c;
		groupsMap.put(moduleGroup, commandableGroup);
		switch(moduleGroup){
		case HOME:
			commandableGroup = homeCommandable();
			break;
		case TOOLS:
			if(userSession.getIsManager()){
				commandableGroup = Builder.instanciateOne().setLabelFromId("command.tools").setIcon(Icon.THING_TOOLS).create();
				commandableGroup.addChild(agendaCommandable());
			}
			break;
		case CONTROL_PANEL:
			if(userSession.getIsManager()){
				commandableGroup = Builder.instanciateOne().setLabelFromId("command.controlpanel").create();
				commandableGroup.addChild(Builder.instanciateOne().setLabelFromId("command.referenceentity").setIcon(Icon.ACTION_SET).setView(ViewType.MODULE_REFERENCE_ENTITY).create());
				//TODO to be rendered when Manager ONLY - use RoleManager as interface
				
				//commandableGroup.addChild("command.security", IconType.THING_SECURITY, ViewType.MODULE_SECURITY, null);
				commandableGroup.addChild(Builder.instanciateOne().setLabelFromId("command.security").setIcon(Icon.THING_SECURITY).setView(ViewType.MODULE_SECURITY).create());
			}
			break;
		case USER_ACCOUNT:
			commandableGroup = Builder.instanciateOne().setLabel(userSession.getUserAccount().getCredentials().getUsername()).create();
			commandableGroup.addChild(notificationsCommandable());
			commandableGroup.addChild(c=Builder.instanciateOne().setLabelFromId("command.useraccount").setIcon(Icon.THING_USERACCOUNT).setView(ViewType.USER_ACCOUNT_CONSULT).create());
			//c.addDefaultParameters();
			//c.addCrudParameters(UIManager.getInstance().getCrudReadParameter(), userSession.getUserAccount());
			c.setIdentifier(COMMANDABLE_USER_ACCOUNT_IDENTIFIER);
			commandableGroup.addChild(logoutCommandable());
			
			break;
		case HELP:
			commandableGroup = Builder.instanciateOne().setLabelFromId("command.help").setIcon(Icon.THING_HELP).create();
			commandableGroup.addChild(Builder.instanciateOne().setLabelFromId("license").setIcon(Icon.THING_LIST).setView(ViewType.LICENCE_READ).create());
			break;
		case REPORT:
			commandableGroup = Builder.instanciateOne().setLabelFromId("command.report").setIcon(Icon.THING_REPORT).create();
			for(SystemMenu systemMenu : systemMenus(userSession)){
				for(UICommandable reportCommandable : systemMenu.getReports())
					commandableGroup.addChild(reportCommandable);
			}
			break;
		}
		for(MenuListener<AbstractUserSession<?, ?>> listener : menuListeners)
			listener.moduleGroupCreated(userSession,moduleGroup, commandableGroup);
		return commandableGroup;
	}
	
	public UICommandable findModuleGroup(ModuleGroup group) {
		for(Entry<ModuleGroup, UICommandable> entry : groupsMap.entrySet())
			if(entry.getKey().equals(group))
				return entry.getValue();
		return null;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Collection<SystemMenu> systemMenus(AbstractUserSession<?,?> userSession){
		Collection<SystemMenu> collection = new ArrayList<>();
		for(AbstractApplicationUIManager applicationUIManager : UIManager.getInstance().getApplicationUImanagers())
			collection.add(applicationUIManager.systemMenu(userSession));
		return collection;
	}
	
	public UIMenu applicationMenu(AbstractUserSession<?,?> userSession){
		logDebug("Build application menu for user {}", userSession.getUser().getCode());
		UIMenu menu = new DefaultMenu();
		menu.addCommandable(createModuleGroup(userSession, ModuleGroup.HOME));
		business(userSession,menu);
		//menu.addCommandable(createModuleGroup(userSession, ModuleGroup.REPORT));
		
		if(Boolean.TRUE.equals(userSession.getIsManager())){
			menu.addCommandable(createModuleGroup(userSession, ModuleGroup.TOOLS));
			menu.addCommandable(createModuleGroup(userSession, ModuleGroup.CONTROL_PANEL));
		}
		menu.addCommandable(createModuleGroup(userSession, ModuleGroup.USER_ACCOUNT));
		//menu.addCommandable(createModuleGroup(userSession, ModuleGroup.HELP));
		return menu;
	}
	
	public UIMenu mobileApplicationMenu(AbstractUserSession<?,?> userSession){
		UIMenu menu = new DefaultMenu();
		menu.addCommandable(homeCommandable());
		mobileBusiness(userSession,menu);
		menu.addCommandable(agendaCommandable());
		menu.addCommandable(notificationsCommandable());
		menu.addCommandable(logoutCommandable());
		return menu;
	}
	
	public UIMenu referenceEntityMenu(AbstractUserSession<?,?> userSession){
		UIMenu menu = new DefaultMenu();
		if(Boolean.TRUE.equals(autoGenerateReferenceEntityMenu)){
			UICommandable p;
			List<BusinessEntityInfos> list = new ArrayList<>(applicationBusiness.findBusinessEntitiesInfos(CrudStrategy.ENUMERATION));
			
			Set<String> categories = new LinkedHashSet<>();
			for(BusinessEntityInfos businessEntityInfos : list){
				categories.add(businessEntityInfos.getClazz().getPackage().getName());
			}
			
			Map<String, UICommandable> map = new LinkedHashMap<>();
			for(String categorie : categories){
				String[] parts = StringUtils.split(categorie,'.');
				UICommandable commandable = Builder.instanciateOne().setLabelFromId(parts[parts.length-1]).create(); //commandable(parts[parts.length-1], null);
				map.put(categorie, commandable);
				menu.addCommandable(commandable);
			}
			
			Collections.sort(list, new BusinessEntityInfosMenuItemComparator());
			for(BusinessEntityInfos businessEntityInfos : list){
				p = Builder.instanciateOne().setLabelFromId(businessEntityInfos.getUserInterface().getLabelId()).create(); //commandable(businessEntityInfos.getUserInterface().getLabelId(), null);
				
				map.get(businessEntityInfos.getClazz().getPackage().getName()).addChild(p);
				//menu.addCommandable(p);
				
				p.setBusinessEntityInfos(businessEntityInfos);
				if(AbstractDataTreeNode.class.isAssignableFrom(businessEntityInfos.getClazz())){
					p.setViewType(ViewType.DYNAMIC_CRUD_MANY);	
				}else{
					p.setViewType(ViewType.DYNAMIC_CRUD_MANY);
				}
			}
		}else{
			for(SystemMenu systemMenu : systemMenus(userSession)){
				for(UICommandable referenceEntityGroup : systemMenu.getReferenceEntities()){
					menu.addCommandable(referenceEntityGroup);
					for(MenuListener<AbstractUserSession<?, ?>> listener : menuListeners)
						listener.referenceEntityGroupCreated(userSession, referenceEntityGroup);
				}
			}
		}
		return menu;
	}
	
	public UIMenu securityMenu(AbstractUserSession<?,?> userSession){
		UIMenu menu = new DefaultMenu();//UICommandable p;
		//if(Boolean.TRUE.equals(userSession.getIsAdministrator()))
		menu.addCommandable(Builder.createList(License.class, null));
		menu.addCommandable(Builder.createList(UniformResourceLocator.class, Icon.THING_URL));
		menu.addCommandable(Builder.createList(Role.class, Icon.THING_ROLE));
		menu.addCommandable(Builder.createList(UserAccount.class, Icon.THING_USERACCOUNT));
		
		return menu;
	}
	
	public UIMenu calendarMenu(AbstractUserSession<?,?> userSession,UserDeviceType userDeviceType){
		UIMenu menu = new DefaultMenu();
		UICommandable p;
		if(!UIManager.getInstance().isMobileDevice(userDeviceType)){
			if(userSession.getIsManager()){
				p = menu.addCommandable(Builder.instanciateOne().setLabelFromId("event.create").setIcon(Icon.ACTION_ADD).setView(ViewType.EVENT_CRUD_ONE).create());
				p.getParameters().add(new Parameter(UIManager.getInstance().getCrudParameter(), UIManager.getInstance().getCrudCreateParameter()));
			}
		}
		return menu;
	}
	
	public UIMenu userAccountMenu(AbstractUserSession<?,?> abstractUserSession) {
		UIMenu menu = new DefaultMenu();
		menu.addCommandable(Builder.instanciateOne().setLabelFromId("user.account.changepassword").setView(ViewType.USER_ACCOUNT_CHANGE_PASSWORD).create());
		return menu;
	}
	
	public UIMenu sessionContextualMenu(AbstractUserSession<?,?> userSession){
		UIMenu menu = new DefaultMenu();
		UICommandable c;
		c = menu.addCommandable("command.notifications", Icon.THING_NOTIFICATIONS, ViewType.NOTIFICATIONS);
		c.setIdentifier(COMMANDABLE_NOTIFICATIONS_IDENTIFIER);
		c = menu.addCommandable("command.calendar", Icon.THING_CALENDAR,ViewType.TOOLS_AGENDA);
		c.setIdentifier(COMMANDABLE_EVENT_CALENDAR_IDENTIFIER);
		menu.addCommandable("command.useraccount.logout", Icon.ACTION_LOGOUT, ViewType.USERACCOUNT_LOGOUT)
			.setCommandRequestType(CommandRequestType.BUSINESS_PROCESSING);
		for(MenuListener<AbstractUserSession<?, ?>> listener : menuListeners)
			listener.sessionContextualMenuCreated(userSession, menu);
		return menu;
	}
	
	/**/
	
	private void business(AbstractUserSession<?,?> userSession,UIMenu menu){
		for(SystemMenu systemMenu : systemMenus(userSession)){
			for(UICommandable businessModuleGroup : systemMenu.getBusinesses()){
				menu.addCommandable(businessModuleGroup);
				for(MenuListener<AbstractUserSession<?, ?>> listener : menuListeners)
					listener.businessModuleGroupCreated(userSession, businessModuleGroup);
			}
		}
	}
	
	private void mobileBusiness(AbstractUserSession<?,?> userSession,UIMenu menu){
		for(SystemMenu systemMenu : systemMenus(userSession)){
			for(UICommandable businessModuleGroup : systemMenu.getMobileBusinesses()){
				menu.addCommandable(businessModuleGroup);
				for(MenuListener<AbstractUserSession<?, ?>> listener : menuListeners)
					listener.businessModuleGroupCreated(userSession, businessModuleGroup);
			}
		}
	}
	
	/* ---- */
	/*
	public UICommandable commandable(String labelId,Icon icon,ViewType viewType){
		UICommandable commandable = Builder.instanciateOne().setLabelFromId(labelId).setIcon(icon).create();
		//commandable.setCommandRequestType(CommandRequestType.UI_VIEW);
		//commandable.setViewType(viewType);
		return commandable;
	}
	
	public UICommandable commandable(CommandRequestType aCommandRequestType, String labelId,IconType iconType){
		UICommandable commandable = createCommandable(null, labelId, iconType, null, null);
		commandable.setCommandRequestType(aCommandRequestType);
		return commandable;
	}
	
	public UICommandable commandable(String labelId,IconType iconType){
		return commandable(CommandRequestType.UI_VIEW, labelId, iconType);
	}
	*/
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
		parameters.setSubjectClass((Class<? extends AbstractIdentifiable>) businessEntityInfos.getClazz());
		parameters.setVerb(Boolean.TRUE);
		c.setLabel(RootBusinessLayer.getInstance().getLanguageBusiness().findDoSomethingText(parameters));
		if(StringUtils.isEmpty(businessEntityInfos.getUserInterface().getEditViewId()))
			c.setViewType(ViewType.DYNAMIC_CRUD_ONE);
		else{
			c.setViewId(businessEntityInfos.getUserInterface().getEditViewId());
			c.getParameters().add(new Parameter(UIManager.getInstance().getClassParameter(), UIManager.getInstance().keyFromClass(businessEntityInfos)));
			c.getParameters().add(new Parameter(UIManager.getInstance().getCrudParameter(), UIManager.getInstance().getCrudCreateParameter()));
		}
		logTrace("Crud one view ID of {} is {}", businessEntityInfos.getClazz().getSimpleName(),c.getViewType()==null?c.getViewId():c.getViewType());
		return c;
	}
	
	public UICommandable crudOne(Class<? extends AbstractIdentifiable> aClass,Icon icon){
		return crudOne(UIManager.getInstance().businessEntityInfos(aClass), icon);
	}
	/*
	public UICommandable crudMany(BusinessEntityInfos businessEntityInfos,Icon icon){
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
	}
	
	public UICommandable crudMany(Class<? extends AbstractIdentifiable> aClass,Icon icon){
		return crudMany(UIManager.getInstance().businessEntityInfos(aClass), icon);
	}
	*/
	/*
	public UICommandable crudMenu(Class<? extends AbstractIdentifiable> aClass){
		UICommandable commandable,p;
		BusinessEntityInfos businessEntityInfos = UIManager.getInstance().businessEntityInfos(aClass);
		commandable = Builder.instanciateOne().setLabelFromId(businessEntityInfos.getUserInterface().getLabelId()).create();
		commandable.getChildren().add(p=crudOne(aClass, Icon.ACTION_ADD));
		p.setLabel(UIManager.getInstance().text("command.item.add"));
		commandable.getChildren().add(p=crudMany(aClass, Icon.THING_LIST));
		p.setLabel(UIManager.getInstance().text("command.list"));
		
		return commandable;
	}
	
	public UICommandable createMany(BusinessEntityInfos businessEntityInfos,Icon icon){
		UICommandable c = crud(businessEntityInfos,null, icon);
		c.setLabel(RootBusinessLayer.getInstance().getLanguageBusiness().findText("command.createmany"+businessEntityInfos.getVarName().toLowerCase()));
		if(StringUtils.isEmpty(businessEntityInfos.getUserInterface().getCreateManyViewId()))
			;
		else{
			c.setViewId(businessEntityInfos.getUserInterface().getCreateManyViewId());
			c.getParameters().add(new Parameter(UIManager.getInstance().getClassParameter(), UIManager.getInstance().keyFromClass(businessEntityInfos)));
			c.getParameters().add(new Parameter(UIManager.getInstance().getCrudParameter(), UIManager.getInstance().getCrudCreateParameter()));
		}
		logTrace("Create many view ID of {} is {}", businessEntityInfos.getClazz().getSimpleName(),c.getViewType()==null?c.getViewId():c.getViewType());
		return c;
	}
	*/
	/*
	public UICommandable createSelectOne(Class<? extends AbstractIdentifiable> aClass,String actionIdentifier,Icon icon){
		return createSelectOne(UIManager.getInstance().businessEntityInfos(aClass),actionIdentifier, icon);
	}
	
	public UICommandable createSelectOne(BusinessEntityInfos businessEntityInfos,String actionIdentifier,Icon icon){
		UICommandable c = crud(businessEntityInfos,null, icon);
		c.setLabel(getSelectCommandableLabel(businessEntityInfos, actionIdentifier));
		if(StringUtils.isEmpty(businessEntityInfos.getUserInterface().getSelectOneViewId()))
			;
		else{
			c.setViewId(businessEntityInfos.getUserInterface().getSelectOneViewId());
			c.setCommandRequestType(CommandRequestType.UI_VIEW);
			c.getParameters().add(new Parameter(UIManager.getInstance().getClassParameter(), UIManager.getInstance().keyFromClass(businessEntityInfos)));
			if(StringUtils.isNotBlank(actionIdentifier))
				c.getParameters().add(new Parameter(UIManager.getInstance().getActionIdentifierParameter(), actionIdentifier));
		}
		logTrace("select one view ID of {} is {}", businessEntityInfos.getClazz().getSimpleName(),c.getViewType()==null?c.getViewId():c.getViewType());
		return c;
	}
	
	public UICommandable createSelectMany(Class<? extends AbstractIdentifiable> aClass,String actionIdentifier,Icon icon){
		return createSelectMany(UIManager.getInstance().businessEntityInfos(aClass),actionIdentifier, icon);
	}
	public UICommandable createSelectMany(BusinessEntityInfos businessEntityInfos,String actionIdentifier,Icon icon){
		UICommandable c = crud(businessEntityInfos,null, icon);
		c.setLabel(getSelectCommandableLabel(businessEntityInfos, actionIdentifier));
		if(StringUtils.isEmpty(businessEntityInfos.getUserInterface().getSelectManyViewId()))
			;
		else{
			c.setViewId(businessEntityInfos.getUserInterface().getSelectManyViewId());
			c.getParameters().add(new Parameter(UIManager.getInstance().getClassParameter(), UIManager.getInstance().keyFromClass(businessEntityInfos)));
			if(StringUtils.isNotBlank(actionIdentifier))
				c.getParameters().add(new Parameter(UIManager.getInstance().getActionIdentifierParameter(), actionIdentifier));
		}
		logTrace("select many view ID of {} is {}", businessEntityInfos.getClazz().getSimpleName(),c.getViewType()==null?c.getViewId():c.getViewType());
		return c;
	}
	*/
	/*
	public UICommandable createMany(Class<? extends AbstractIdentifiable> aClass,Icon icon){
		return createMany(UIManager.getInstance().businessEntityInfos(aClass), icon);
	}
	*/
	public String getSelectCommandableLabel(BusinessEntityInfos businessEntityInfos,String actionIdentifier){
		return RootBusinessLayer.getInstance().getLanguageBusiness().findText("command.select"+businessEntityInfos.getVarName().toLowerCase()
				+ (StringUtils.isBlank(actionIdentifier)?Constant.EMPTY_STRING:(Constant.CHARACTER_DOT+actionIdentifier)));
	}
	
	public String getCommandableLabel(BusinessEntityInfos businessEntityInfos,String actionIdentifier){
		return RootBusinessLayer.getInstance().getLanguageBusiness().findText(businessEntityInfos.getVarName().toLowerCase()
				+ (StringUtils.isBlank(actionIdentifier)?Constant.EMPTY_STRING:(Constant.CHARACTER_DOT+actionIdentifier)));
	}
	
	/**/
	
	private static class BusinessEntityInfosMenuItemComparator implements Comparator<BusinessEntityInfos>{

		@Override
		public int compare(BusinessEntityInfos o1, BusinessEntityInfos o2) {
			return o1.getClazz().getName().compareTo(o2.getClazz().getName());
		}
		
	}

}
