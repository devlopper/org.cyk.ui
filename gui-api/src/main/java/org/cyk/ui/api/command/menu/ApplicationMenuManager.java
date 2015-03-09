package org.cyk.ui.api.command.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.Getter;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.ui.api.UIProvider;
import org.cyk.ui.api.UserSession;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.CommandRequestType;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.api.command.UICommandable.ViewType;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;

@Singleton @Deployment(initialisationType=InitialisationType.EAGER)
public class ApplicationMenuManager extends AbstractMenu implements Serializable {

	private static final long serialVersionUID = 4331240830505008164L;
	public static enum ModuleGroup{CONTROL_PANEL,REPORT,USER_ACCOUNT,HELP}
	
	private static ApplicationMenuManager INSTANCE;
	public static ApplicationMenuManager getInstance() {
		return INSTANCE;
	}
	
	@Inject private ApplicationBusiness applicationBusiness;
	
	private Map<ModuleGroup, UICommandable> groupsMap = new HashMap<>();
	@Getter private Collection<SystemMenu> systemMenus = new ArrayList<>();
	private Collection<ApplicationMenuListener> applicationMenuListeners = new ArrayList<>();
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public UICommandable createModuleGroup(UserSession userSession,ModuleGroup moduleGroup) {
		UICommandable commandableGroup = null;
		groupsMap.put(moduleGroup, commandableGroup);
		switch(moduleGroup){
		case CONTROL_PANEL:
			commandableGroup = UIProvider.getInstance().createCommandable("command.controlpanel", null);
			commandableGroup.addChild("command.referenceentity", null, ViewType.MODULE_REFERENCE_ENTITY, null);
			break;
		case USER_ACCOUNT:
			commandableGroup = UIProvider.getInstance().createCommandable("command.useraccount", IconType.THING_USERACCOUNT);
			commandableGroup.addChild("command.useraccount.logout", IconType.ACTION_LOGOUT, ViewType.USERACCOUNT_LOGOUT, null)
				.setCommandRequestType(CommandRequestType.BUSINESS_PROCESSING);
			break;
		case HELP:
			commandableGroup = UIProvider.getInstance().createCommandable("command.help", null);
			break;
		case REPORT:
			commandableGroup = UIProvider.getInstance().createCommandable("command.report", null);
			for(SystemMenu systemMenu : systemMenus){
				for(UICommandable reportCommandable : systemMenu.getReports())
					commandableGroup.addChild(reportCommandable);
			}
			break;
		}
		for(ApplicationMenuListener listener : applicationMenuListeners)
			listener.moduleGroupCreated(userSession,moduleGroup, commandableGroup);
		return commandableGroup;
	}
	
	public UICommandable findModuleGroup(ModuleGroup group) {
		for(Entry<ModuleGroup, UICommandable> entry : groupsMap.entrySet())
			if(entry.getKey().equals(group))
				return entry.getValue();
		return null;
	}
	
	public UIMenu build(UserSession userSession){
		//System.out.println("ApplicationMenuManager.build()");
		UIMenu menu = new DefaultMenu();
		business(userSession,menu);
		menu.addCommandable(createModuleGroup(userSession, ModuleGroup.REPORT));
		menu.addCommandable(createModuleGroup(userSession, ModuleGroup.CONTROL_PANEL));
		menu.addCommandable(createModuleGroup(userSession, ModuleGroup.USER_ACCOUNT));
		return menu;
	}
	
	public UIMenu referenceEntity(UserSession userSession){
		UIMenu menu = new DefaultMenu();
		UICommandable p;
		List<BusinessEntityInfos> list = new ArrayList<>(applicationBusiness.findBusinessEntitiesInfos(CrudStrategy.ENUMERATION));
		Collections.sort(list, new BusinessEntityInfosMenuItemComparator());
		for(BusinessEntityInfos businessEntityInfos : list){
			menu.addCommandable(p = UIProvider.getInstance().createCommandable(businessEntityInfos.getUiLabelId(), null));
			p.setBusinessEntityInfos(businessEntityInfos);
			if(AbstractDataTreeNode.class.isAssignableFrom(businessEntityInfos.getClazz())){
				p.setViewType(ViewType.DYNAMIC_CRUD_MANY);	
			}else{
				p.setViewType(ViewType.DYNAMIC_CRUD_MANY);
			}
		}
		return menu;
	}
	
	/**/
	
	private void business(UserSession userSession,UIMenu menu){
		for(SystemMenu systemMenu : systemMenus){
			for(UICommandable businessModuleGroup : systemMenu.getBusinesses()){
				menu.addCommandable(businessModuleGroup);
				for(ApplicationMenuListener listener : applicationMenuListeners)
					listener.businessModuleGroupCreated(userSession, businessModuleGroup);
			}
		}
	}
	
	/**/
	
	private static class BusinessEntityInfosMenuItemComparator implements Comparator<BusinessEntityInfos>{

		@Override
		public int compare(BusinessEntityInfos o1, BusinessEntityInfos o2) {
			return o1.getClazz().getName().compareTo(o2.getClazz().getName());
		}
		
	}
	
}
