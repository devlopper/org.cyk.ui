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
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.system.root.model.security.Role;
import org.cyk.ui.api.AbstractApplicationUIManager;
import org.cyk.ui.api.AbstractUserSession;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.UIProvider;
import org.cyk.ui.api.UserDeviceType;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.CommandRequestType;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.api.command.UICommandable.Parameter;
import org.cyk.ui.api.command.UICommandable.ViewType;
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
	
	@Getter private Collection<MenuListener> menuListeners = new ArrayList<>();
	@Getter @Setter private Boolean autoGenerateReferenceEntityMenu=Boolean.FALSE;
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	private UICommandable homeCommandable(){
		UICommandable commandable = UIProvider.getInstance().createCommandable("command.home", IconType.THING_HOME,ViewType.HOME);
		commandable.setCommandRequestType(CommandRequestType.UI_VIEW);
		return commandable;
	}
	
	private UICommandable logoutCommandable(){
		UICommandable commandable = UIProvider.getInstance().createCommandable("command.useraccount.logout", IconType.ACTION_LOGOUT, ViewType.USERACCOUNT_LOGOUT);
		commandable.setCommandRequestType(CommandRequestType.BUSINESS_PROCESSING);
		return commandable;
	}
	
	private UICommandable notificationsCommandable(){
		UICommandable commandable = UIProvider.getInstance().createCommandable("command.notifications", IconType.THING_NOTIFICATIONS, ViewType.NOTIFICATIONS);
		commandable.setCommandRequestType(CommandRequestType.UI_VIEW);
		commandable.setIdentifier(COMMANDABLE_NOTIFICATIONS_IDENTIFIER);
		return commandable;
	}
	
	private UICommandable agendaCommandable(){
		UICommandable commandable = UIProvider.getInstance().createCommandable("command.agenda", IconType.THING_CALENDAR, ViewType.TOOLS_AGENDA);
		commandable.setCommandRequestType(CommandRequestType.UI_VIEW);
		commandable.setIdentifier(COMMANDABLE_EVENT_CALENDAR_IDENTIFIER);
		return commandable;
	}
	
	public UICommandable createModuleGroup(AbstractUserSession userSession,ModuleGroup moduleGroup) {
		Boolean moduleGroupCreateable = Boolean.TRUE;
		for(MenuListener listener : menuListeners){
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
				commandableGroup = UIProvider.getInstance().createCommandable("command.tools", IconType.THING_TOOLS);
				commandableGroup.addChild(agendaCommandable() /*"command.calendar", IconType.THING_CALENDAR, ViewType.TOOLS_CALENDAR, null*/);
			}
			break;
		case CONTROL_PANEL:
			if(userSession.getIsManager()){
				commandableGroup = UIProvider.getInstance().createCommandable("command.controlpanel", null);
				commandableGroup.addChild("command.referenceentity", IconType.ACTION_SET, ViewType.MODULE_REFERENCE_ENTITY, null);
				//TODO to be rendered when Manager ONLY - use RoleManager as interface
				commandableGroup.addChild("command.security", IconType.THING_SECURITY, ViewType.MODULE_SECURITY, null);
			}
			break;
		case USER_ACCOUNT:
			commandableGroup = UIProvider.getInstance().createCommandable(/*"command.useraccount"*/ userSession.getUserAccount().getCredentials().getUsername(),
					null);
			commandableGroup.setLabel(userSession.getUserAccount().getCredentials().getUsername());
			commandableGroup.addChild(notificationsCommandable());
			c = commandableGroup.addChild("command.useraccount", IconType.THING_USERACCOUNT, ViewType.USER_ACCOUNT_CONSULT, null);
			c.setIdentifier(COMMANDABLE_USER_ACCOUNT_IDENTIFIER);
			commandableGroup.addChild(logoutCommandable());
			
			break;
		case HELP:
			commandableGroup = UIProvider.getInstance().createCommandable("command.help", IconType.THING_HELP);
			commandableGroup.addChild("license", IconType.THING_LIST, ViewType.LICENCE_READ, null);
			break;
		case REPORT:
			commandableGroup = UIProvider.getInstance().createCommandable("command.report", IconType.THING_REPORT);
			for(SystemMenu systemMenu : systemMenus(userSession)){
				for(UICommandable reportCommandable : systemMenu.getReports())
					commandableGroup.addChild(reportCommandable);
			}
			break;
		}
		for(MenuListener listener : menuListeners)
			listener.moduleGroupCreated(userSession,moduleGroup, commandableGroup);
		return commandableGroup;
	}
	
	public UICommandable findModuleGroup(ModuleGroup group) {
		for(Entry<ModuleGroup, UICommandable> entry : groupsMap.entrySet())
			if(entry.getKey().equals(group))
				return entry.getValue();
		return null;
	}
	
	private Collection<SystemMenu> systemMenus(AbstractUserSession userSession){
		Collection<SystemMenu> collection = new ArrayList<>();
		for(AbstractApplicationUIManager applicationUIManager : UIManager.getInstance().getApplicationUImanagers())
			collection.add(applicationUIManager.systemMenu(userSession));
		return collection;
	}
	
	public UIMenu applicationMenu(AbstractUserSession userSession){
		logDebug("Build application menu for user {}", userSession.getUser().getCode());
		UIMenu menu = new DefaultMenu();
		menu.addCommandable(createModuleGroup(userSession, ModuleGroup.HOME));
		business(userSession,menu);
		//menu.addCommandable(createModuleGroup(userSession, ModuleGroup.REPORT));
		
		if(userSession.getIsManager()){
			menu.addCommandable(createModuleGroup(userSession, ModuleGroup.TOOLS));
			menu.addCommandable(createModuleGroup(userSession, ModuleGroup.CONTROL_PANEL));
		}
		menu.addCommandable(createModuleGroup(userSession, ModuleGroup.USER_ACCOUNT));
		//menu.addCommandable(createModuleGroup(userSession, ModuleGroup.HELP));
		return menu;
	}
	
	public UIMenu mobileApplicationMenu(AbstractUserSession userSession){
		UIMenu menu = new DefaultMenu();
		menu.addCommandable(homeCommandable());
		mobileBusiness(userSession,menu);
		menu.addCommandable(agendaCommandable());
		menu.addCommandable(notificationsCommandable());
		menu.addCommandable(logoutCommandable());
		return menu;
	}
	
	public UIMenu referenceEntityMenu(AbstractUserSession userSession){
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
				UICommandable commandable = commandable(parts[parts.length-1], null);
				map.put(categorie, commandable);
				menu.addCommandable(commandable);
			}
			
			Collections.sort(list, new BusinessEntityInfosMenuItemComparator());
			for(BusinessEntityInfos businessEntityInfos : list){
				p = commandable(businessEntityInfos.getUiLabelId(), null);
				
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
					for(MenuListener listener : menuListeners)
						listener.referenceEntityGroupCreated(userSession, referenceEntityGroup);
				}
			}
		}
		return menu;
	}
	
	public UIMenu securityMenu(AbstractUserSession userSession){
		UIMenu menu = new DefaultMenu();UICommandable p;
		menu.addCommandable(p = commandable("user.account.create", IconType.ACTION_ADD,ViewType.USER_ACCOUNT_CRUD_ONE));
		p.getParameters().add(new Parameter(UIManager.getInstance().getCrudParameter(), UIManager.getInstance().getCrudCreateParameter()));
		menu.addCommandable(commandable("user.accounts", IconType.THING_LIST,ViewType.USER_ACCOUNTS));
		
		menu.addCommandable(commandable("roles", IconType.THING_LIST).addReadAllParameters(Role.class));
		return menu;
	}
	
	public UIMenu calendarMenu(AbstractUserSession userSession,UserDeviceType userDeviceType){
		UIMenu menu = new DefaultMenu();
		UICommandable p;
		if(!UIManager.getInstance().isMobileDevice(userDeviceType)){
			if(userSession.getIsManager()){
				p = menu.addCommandable(commandable("event.create", IconType.ACTION_ADD,ViewType.EVENT_CRUD_ONE));
				p.getParameters().add(new Parameter(UIManager.getInstance().getCrudParameter(), UIManager.getInstance().getCrudCreateParameter()));
			}
		}
		return menu;
	}
	
	public UIMenu userAccountMenu(AbstractUserSession abstractUserSession) {
		UIMenu menu = new DefaultMenu();
		menu.addCommandable(commandable("user.account.changepassword", null,ViewType.USER_ACCOUNT_CHANGE_PASSWORD));
		
		return menu;
	}
	
	public UIMenu sessionContextualMenu(AbstractUserSession userSession){
		UIMenu menu = new DefaultMenu();
		UICommandable c;
		c = menu.addCommandable("command.notifications", IconType.THING_NOTIFICATIONS, ViewType.NOTIFICATIONS);
		c.setIdentifier(COMMANDABLE_NOTIFICATIONS_IDENTIFIER);
		c = menu.addCommandable("command.calendar", IconType.THING_CALENDAR,ViewType.TOOLS_AGENDA);
		c.setIdentifier(COMMANDABLE_EVENT_CALENDAR_IDENTIFIER);
		menu.addCommandable("command.useraccount.logout", IconType.ACTION_LOGOUT, ViewType.USERACCOUNT_LOGOUT)
			.setCommandRequestType(CommandRequestType.BUSINESS_PROCESSING);
		for(MenuListener listener : menuListeners)
			listener.sessionContextualMenuCreated(userSession, menu);
		return menu;
	}
	
	/**/
	
	private void business(AbstractUserSession userSession,UIMenu menu){
		for(SystemMenu systemMenu : systemMenus(userSession)){
			for(UICommandable businessModuleGroup : systemMenu.getBusinesses()){
				menu.addCommandable(businessModuleGroup);
				for(MenuListener listener : menuListeners)
					listener.businessModuleGroupCreated(userSession, businessModuleGroup);
			}
		}
	}
	
	private void mobileBusiness(AbstractUserSession userSession,UIMenu menu){
		for(SystemMenu systemMenu : systemMenus(userSession)){
			for(UICommandable businessModuleGroup : systemMenu.getMobileBusinesses()){
				menu.addCommandable(businessModuleGroup);
				for(MenuListener listener : menuListeners)
					listener.businessModuleGroupCreated(userSession, businessModuleGroup);
			}
		}
	}
	
	/* ---- */
	
	public UICommandable commandable(String labelId,IconType iconType,ViewType viewType){
		UICommandable commandable = UIProvider.getInstance().createCommandable(null, labelId, iconType, null, null);
		commandable.setCommandRequestType(CommandRequestType.UI_VIEW);
		commandable.setViewType(viewType);
		return commandable;
	}
	
	public UICommandable commandable(CommandRequestType aCommandRequestType, String labelId,IconType iconType){
		UICommandable commandable = UIProvider.getInstance().createCommandable(null, labelId, iconType, null, null);
		commandable.setCommandRequestType(aCommandRequestType);
		return commandable;
	}
	
	public UICommandable commandable(String labelId,IconType iconType){
		return commandable(CommandRequestType.UI_VIEW, labelId, iconType);
	}
	
	private UICommandable crud(BusinessEntityInfos businessEntityInfos,ViewType viewType,IconType iconType){
		UICommandable commandable = commandable(businessEntityInfos.getUiLabelId(), iconType);
		commandable.setBusinessEntityInfos(businessEntityInfos);
		commandable.setViewType(viewType);
		return commandable;
	}
	
	public UICommandable crudOne(BusinessEntityInfos businessEntityInfos,IconType iconType){
		UICommandable c = crud(businessEntityInfos,null, iconType);
		if(StringUtils.isEmpty(businessEntityInfos.getUiEditViewId()))
			c.setViewType(ViewType.DYNAMIC_CRUD_ONE);
		else{
			c.setViewId(businessEntityInfos.getUiEditViewId());
			c.getParameters().add(new Parameter(UIManager.getInstance().getClassParameter(), UIManager.getInstance().keyFromClass(businessEntityInfos)));
			c.getParameters().add(new Parameter(UIManager.getInstance().getCrudParameter(), UIManager.getInstance().getCrudCreateParameter()));
		}
		logTrace("Crud one view ID of {} is {}", businessEntityInfos.getClazz().getSimpleName(),c.getViewType()==null?c.getViewId():c.getViewType());
		return c;
	}
	
	public UICommandable crudOne(Class<? extends AbstractIdentifiable> aClass,IconType iconType){
		return crudOne(UIManager.getInstance().businessEntityInfos(aClass), iconType);
	}
	
	public UICommandable crudMany(BusinessEntityInfos businessEntityInfos,IconType iconType){
		UICommandable c = crud(businessEntityInfos, null, iconType);
		//c.setLabel(UIManager.getInstance().getLanguageBusiness().findText("list.of",
		//		new Object[]{UIManager.getInstance().getLanguageBusiness().findText(businessEntityInfos.getUiLabelId())}));
		if(StringUtils.isEmpty(businessEntityInfos.getUiListViewId()))
			c.setViewType(ViewType.DYNAMIC_CRUD_MANY);
		else{
			c.setViewId(businessEntityInfos.getUiListViewId());
			c.setCommandRequestType(CommandRequestType.UI_VIEW);
			c.getParameters().add(new Parameter(UIManager.getInstance().getClassParameter(), UIManager.getInstance().keyFromClass(businessEntityInfos)));
		}
		return c;
	}
	
	public UICommandable crudMany(Class<? extends AbstractIdentifiable> aClass,IconType iconType){
		return crudMany(UIManager.getInstance().businessEntityInfos(aClass), iconType);
	}
	
	public UICommandable crudMenu(Class<? extends AbstractIdentifiable> aClass){
		UICommandable commandable,p;
		BusinessEntityInfos businessEntityInfos = UIManager.getInstance().businessEntityInfos(aClass);
		commandable = commandable(businessEntityInfos.getUiLabelId(), null);
		commandable.getChildren().add(p=crudOne(aClass, IconType.ACTION_ADD));
		p.setLabel(UIManager.getInstance().text("command.item.add"));
		commandable.getChildren().add(p=crudMany(aClass, IconType.THING_LIST));
		p.setLabel(UIManager.getInstance().text("command.list"));
		
		return commandable;
	}
	
	public UICommandable createMany(BusinessEntityInfos businessEntityInfos,IconType iconType){
		UICommandable c = crud(businessEntityInfos,null, iconType);
		c.setLabel(RootBusinessLayer.getInstance().getLanguageBusiness().findText("command.createmany"+businessEntityInfos.getVarName().toLowerCase()));
		if(StringUtils.isEmpty(businessEntityInfos.getUiCreateManyViewId()))
			;
		else{
			c.setViewId(businessEntityInfos.getUiCreateManyViewId());
			c.getParameters().add(new Parameter(UIManager.getInstance().getClassParameter(), UIManager.getInstance().keyFromClass(businessEntityInfos)));
			c.getParameters().add(new Parameter(UIManager.getInstance().getCrudParameter(), UIManager.getInstance().getCrudCreateParameter()));
		}
		logTrace("Create many view ID of {} is {}", businessEntityInfos.getClazz().getSimpleName(),c.getViewType()==null?c.getViewId():c.getViewType());
		return c;
	}
	public UICommandable createMany(Class<? extends AbstractIdentifiable> aClass,IconType iconType){
		return createMany(UIManager.getInstance().businessEntityInfos(aClass), iconType);
	}
	
	/**/
	
	private static class BusinessEntityInfosMenuItemComparator implements Comparator<BusinessEntityInfos>{

		@Override
		public int compare(BusinessEntityInfos o1, BusinessEntityInfos o2) {
			return o1.getClazz().getName().compareTo(o2.getClazz().getName());
		}
		
	}
	
}
