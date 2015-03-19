package org.cyk.ui.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.Getter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.ui.api.MenuListenerOLD.ModuleType;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.CommandRequestType;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.api.command.UICommandable.Parameter;
import org.cyk.ui.api.command.UICommandable.ViewType;
import org.cyk.ui.api.command.menu.DefaultMenu;
import org.cyk.ui.api.command.menu.UIMenu;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.cdi.AbstractBean;

@Singleton @Deprecated
public class MenuManagerOLD extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -59229034680588410L;

	public enum Type {APPLICATION,CONTEXTUAL}
	
	private static MenuManagerOLD INSTANCE;
	public static MenuManagerOLD getInstance() {
		return INSTANCE;
	}
	
	@Inject protected ApplicationBusiness applicationBusiness;
	@Getter private Collection<MenuListenerOLD> menuListeners = new ArrayList<>();
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public UIMenu build(UserSession userSession,Type type,InternalApplicationModuleType internalApplicationModuleType){
		UIMenu menu = null;
		if(Boolean.TRUE.equals(userSession.getLoggedIn())){
			menu = new DefaultMenu();
			switch(type){
			case APPLICATION:application(userSession,menu,internalApplicationModuleType);break;
			case CONTEXTUAL:contextual(menu,internalApplicationModuleType); break;
			}
			if(!Boolean.TRUE.equals(userSession.getIsAdministrator()))
				for(MenuListenerOLD listener : menuListeners)
					listener.menu(userSession,menu, type);
		}
		return menu;
	}
	
	public UIMenu build(UserSession userSession,Type type){
		return build(userSession,type, null);
	}
	
	/**/
	
	private void application(UserSession userSession,UIMenu aMenu,InternalApplicationModuleType internalApplicationModuleType){
		if(Boolean.TRUE.equals(userSession.getIsAdministrator())){
			administration(userSession, aMenu);
		}else{
			if(internalApplicationModuleType==null){
				aMenu.getCommandables().addAll(menuItemBusinessModules(userSession,aMenu));
				aMenu.getCommandables().add(menuItemControlPanelModule(userSession,aMenu));
				//aMenu.getCommandables().add(menuItemReferenceEntity(applicationBusiness));
				aMenu.getCommandables().add(menuItemHelpModule(userSession,aMenu));
				aMenu.getCommandables().add(menuItemUserAccountModule(userSession,aMenu));
				
			}else{
				switch(internalApplicationModuleType){
				case CRUD:
					crud(aMenu);
					break;
				}
			}
		}
		
	}
	
	private UICommandable moduleEvent(UserSession userSession,UIMenu aMenu,UICommandable module,ModuleType type){
		for(MenuListenerOLD listener : menuListeners)
			module = listener.module(userSession, aMenu, module, type);
		return module;
	}
	
	private Collection<UICommandable> moduleEvent(UserSession userSession,UIMenu aMenu,Collection<UICommandable> modules,ModuleType type){
		for(MenuListenerOLD listener : menuListeners)
			modules = listener.modules(userSession, aMenu, modules, type);
		return modules;
	}
	
	public Collection<UICommandable> menuItemBusinessModules(UserSession userSession,UIMenu aMenu){
		Collection<UICommandable> modules = new ArrayList<>();
		
		return moduleEvent(userSession, aMenu, modules, ModuleType.BUSINESS);
	}
	
	public UICommandable menuItemControlPanelModule(UserSession userSession,UIMenu aMenu){
		UICommandable module = UIProvider.getInstance().createCommandable("command.controlpanel", null),p;
		//module.addChild(p = commandable("command.referenceentity",null));
		//p.setViewType(ViewType.MODULE_REFERENCE_ENTITY);
		return moduleEvent(userSession, aMenu, module, ModuleType.CONTROL_PANEL);
	}
	
	public UICommandable menuItemHelpModule(UserSession userSession,UIMenu aMenu){
		UICommandable module = UIProvider.getInstance().createCommandable("command.help", IconType.ACTION_HELP);
		
		return moduleEvent(userSession, aMenu, module, ModuleType.HELP);
	}
	
	public UICommandable menuItemUserAccountModule(UserSession userSession,UIMenu aMenu){
		UICommandable module = commandable("command.useraccount", IconType.THING_USERACCOUNT);
				
		UICommandable logout = commandable("command.useraccount.logout", IconType.ACTION_LOGOUT);
		logout.setViewType(ViewType.USERACCOUNT_LOGOUT);
		logout.setCommandRequestType(CommandRequestType.BUSINESS_PROCESSING);
		
		module.getChildren().add(logout);
		
		return moduleEvent(userSession, aMenu, module, ModuleType.USER_ACCOUNT);
	}
	
	private void administration(UserSession userSession,UIMenu aMenu){
		UICommandable commandable,p;
		aMenu.getCommandables().add(commandable = commandable("command.application", IconType.THING_APPLICATION));
		commandable.getChildren().add( p = commandable("command.application.license", null));
		p.setViewType(ViewType.LICENCE);
		
		aMenu.getCommandables().add(menuItemUserAccountModule(userSession,aMenu));
	}
	
	private void contextual(UIMenu aMenu,InternalApplicationModuleType internalApplicationModuleType){
		if(internalApplicationModuleType==null){
			
		}else{
			switch(internalApplicationModuleType){
			case CRUD:
				crud(aMenu);
				break;
			}
		}
	}
	
	/**/
	
	private void crud(UIMenu aMenu){
		UICommandable commandable;
		aMenu.getCommandables().add(commandable = commandable("command.administration", IconType.ACTION_ADMINISTRATE));
		List<BusinessEntityInfos> list = new ArrayList<>(applicationBusiness.findBusinessEntitiesInfos(CrudStrategy.ENUMERATION));
		Collections.sort(list, new BusinessEntityInfosMenuItemComparator());
		
		commandable.getChildren().add(commandable("command.administration", IconType.ACTION_ADMINISTRATE));
		
	}
	
	/**/
	
	
	public static UICommandable commandable(CommandRequestType aCommandRequestType, String labelId,IconType iconType){
		UICommandable commandable = UIProvider.getInstance().createCommandable(null, labelId, iconType, null, null);
		commandable.setCommandRequestType(aCommandRequestType);
		return commandable;
	}
	
	public static UICommandable commandable(String labelId,IconType iconType){
		return commandable(CommandRequestType.UI_VIEW, labelId, iconType);
	}
	
	private static UICommandable crud(BusinessEntityInfos businessEntityInfos,ViewType viewType,IconType iconType){
		UICommandable commandable = commandable(businessEntityInfos.getUiLabelId(), iconType);
		commandable.setBusinessEntityInfos(businessEntityInfos);
		commandable.setViewType(viewType);
		return commandable;
	}
	
	public static UICommandable crudOne(BusinessEntityInfos businessEntityInfos,IconType iconType){
		UICommandable c = crud(businessEntityInfos,null, iconType);
		if(StringUtils.isEmpty(businessEntityInfos.getUiEditViewId()))
			c.setViewType(ViewType.DYNAMIC_CRUD_ONE);
		else{
			c.setViewId(businessEntityInfos.getUiEditViewId());
			c.getParameters().add(new Parameter(UIManager.getInstance().getClassParameter(), UIManager.getInstance().keyFromClass(businessEntityInfos)));
			c.getParameters().add(new Parameter(UIManager.getInstance().getCrudParameter(), UIManager.getInstance().getCrudCreateParameter()));
		}
		return c;
	}
	
	public static UICommandable crudOne(Class<? extends AbstractIdentifiable> aClass,IconType iconType){
		return crudOne(UIManager.getInstance().businessEntityInfos(aClass), iconType);
	}
	
	public static UICommandable crudMany(BusinessEntityInfos businessEntityInfos,IconType iconType){
		UICommandable c = crud(businessEntityInfos, null, iconType);
		if(StringUtils.isEmpty(businessEntityInfos.getUiListViewId()))
			c.setViewType(ViewType.DYNAMIC_CRUD_MANY);
		else{
			c.setViewId(businessEntityInfos.getUiListViewId());
			c.getParameters().add(new Parameter(UIManager.getInstance().getClassParameter(), UIManager.getInstance().keyFromClass(businessEntityInfos)));
		}
		return c;
	}
	
	public static UICommandable crudMany(Class<? extends AbstractIdentifiable> aClass,IconType iconType){
		return crudMany(UIManager.getInstance().businessEntityInfos(aClass), iconType);
	}
	
	public static UICommandable crudMenu(Class<? extends AbstractIdentifiable> aClass){
		UICommandable commandable,p;
		BusinessEntityInfos businessEntityInfos = UIManager.getInstance().businessEntityInfos(aClass);
		commandable = commandable(businessEntityInfos.getUiLabelId(), null);
		commandable.getChildren().add(p=crudOne(aClass, IconType.ACTION_ADD));
		p.setLabel(UIManager.getInstance().text("command.item.add"));
		commandable.getChildren().add(p=crudMany(aClass, IconType.THING_LIST));
		p.setLabel(UIManager.getInstance().text("command.list"));
		
		return commandable;
	}
	
	/**/
	
	
	
	public static UICommandable menuItemReferenceEntity(ApplicationBusiness applicationBusiness){
		UICommandable p;
		UICommandable referenceEntity = commandable("command.referenceentity", null);
		
		List<BusinessEntityInfos> list = new ArrayList<>(applicationBusiness.findBusinessEntitiesInfos(CrudStrategy.ENUMERATION));
		Collections.sort(list, new BusinessEntityInfosMenuItemComparator());
		for(BusinessEntityInfos businessEntityInfos : list){
			referenceEntity.getChildren().add( p = commandable(businessEntityInfos.getUiLabelId(), null));
			p.setBusinessEntityInfos(businessEntityInfos);
			if(AbstractDataTreeNode.class.isAssignableFrom(businessEntityInfos.getClazz())){
				p.setViewType(ViewType.DYNAMIC_CRUD_MANY);	
			}else{
				p.setViewType(ViewType.DYNAMIC_CRUD_MANY);
			}
		}
		return referenceEntity;
	}
	
	public static UICommandable menuItemControlPanel(ApplicationBusiness applicationBusiness){
		UICommandable commandable = commandable("command.controlpanel", IconType.THING_CONTROLPANEL);
		commandable.getChildren().add(menuItemReferenceEntity(applicationBusiness));
		return commandable;
	}
	
	
	
	/**/
		
	private static class BusinessEntityInfosMenuItemComparator implements Comparator<BusinessEntityInfos>{

		@Override
		public int compare(BusinessEntityInfos o1, BusinessEntityInfos o2) {
			return o1.getClazz().getName().compareTo(o2.getClazz().getName());
		}
		
	}
	
}
