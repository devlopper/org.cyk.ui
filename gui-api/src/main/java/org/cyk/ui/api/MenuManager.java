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

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.ui.api.command.DefaultMenu;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.CommandRequestType;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.api.command.UICommandable.ViewType;
import org.cyk.ui.api.command.UIMenu;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.cdi.AbstractBean;

@Singleton
public class MenuManager extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -59229034680588410L;

	public enum Type {APPLICATION,CONTEXTUAL}
	
	private static MenuManager INSTANCE;
	public static MenuManager getInstance() {
		return INSTANCE;
	}
	
	@Inject protected ApplicationBusiness applicationBusiness;
	
	@Getter private Collection<MenuListener> menuListeners = new ArrayList<>();
	
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
				for(MenuListener listener : menuListeners)
					listener.menu(userSession,menu, type);
		}
		return menu;
	}
	
	public UIMenu build(UserSession userSession,Type type){
		return build(userSession,type, null);
	}
	
	/**/
	
	private void application(UserSession userSession,UIMenu aMenu,InternalApplicationModuleType internalApplicationModuleType){
		//aMenu.getCommandables().add(commandable("command.file", "ui-icon-file"));
		if(Boolean.TRUE.equals(userSession.getIsAdministrator())){
			administration(userSession, aMenu);
		}else{
			if(internalApplicationModuleType==null){
				UICommandable commandable,p;
				
				aMenu.getCommandables().add(commandable = commandable("command.tools", IconType.THING_TOOLS));
				commandable.getChildren().add( p = commandable("command.tools.calendar", null));
				p.setViewType(ViewType.TOOLS_CALENDAR);
				
				aMenu.getCommandables().add(commandable = commandable("command.administration", IconType.ACTION_ADMINISTRATE));
				List<BusinessEntityInfos> list = new ArrayList<>(applicationBusiness.findBusinessEntitiesInfos(CrudStrategy.ENUMERATION));
				Collections.sort(list, new BusinessEntityInfosMenuItemComparator());
				for(BusinessEntityInfos businessEntityInfos : list){
					commandable.getChildren().add( p = commandable(businessEntityInfos.getUiLabelId(), null));
					p.setBusinessEntityInfos(businessEntityInfos);
					if(AbstractDataTreeNode.class.isAssignableFrom(businessEntityInfos.getClazz())){
						p.setViewType(ViewType.DYNAMIC_CRUD_MANY);	
					}else{
						p.setViewType(ViewType.DYNAMIC_CRUD_MANY);
					}
				}
					//aMenu.getCommandables().add(commandable(aClass.getSimpleName(), null));
				aMenu.getCommandables().add(commandable("command.help", IconType.ACTION_HELP));
				
				aMenu.getCommandables().add(menuItemUserAccount());
				
			}else{
				switch(internalApplicationModuleType){
				case CRUD:
					crud(aMenu);
					break;
				}
			}
		}
		
	}
	
	private void administration(UserSession userSession,UIMenu aMenu){
		UICommandable commandable,p;
		aMenu.getCommandables().add(commandable = commandable("command.application", IconType.THING_APPLICATION));
		commandable.getChildren().add( p = commandable("command.application.license", null));
		p.setViewType(ViewType.LICENCE);
		
		aMenu.getCommandables().add(menuItemUserAccount());
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
		for(BusinessEntityInfos businessEntityInfos : list){
			commandable.getChildren().add(crudMany(businessEntityInfos, null));
			/*p.setBusinessEntityInfos(businessEntityInfos);
			if(AbstractDataTreeNode.class.isAssignableFrom(businessEntityInfos.getClazz())){
				p.setViewType(ViewType.DYNAMIC_CRUD_MANY);	
			}else{
				p.setViewType(ViewType.DYNAMIC_CRUD_MANY);
			}*/
		}
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
		return crud(businessEntityInfos, ViewType.DYNAMIC_CRUD_ONE, iconType);
	}
	
	public static UICommandable crudOne(Class<? extends AbstractIdentifiable> aClass,IconType iconType){
		return crudOne(UIManager.getInstance().businessEntityInfos(aClass), iconType);
	}
	
	public static UICommandable crudMany(BusinessEntityInfos businessEntityInfos,IconType iconType){
		return crud(businessEntityInfos, ViewType.DYNAMIC_CRUD_MANY, iconType);
	}
	
	public static UICommandable crudMany(Class<? extends AbstractIdentifiable> aClass,IconType iconType){
		return crudMany(UIManager.getInstance().businessEntityInfos(aClass), iconType);
	}
	
	/**/
	
	public static UICommandable menuItemUserAccount(){
		UICommandable userAccount = commandable("command.useraccount", IconType.THING_USERACCOUNT);
				
		UICommandable logout = commandable("command.useraccount.logout", IconType.ACTION_LOGOUT);
		logout.setViewType(ViewType.USERACCOUNT_LOGOUT);
		logout.setCommandRequestType(CommandRequestType.BUSINESS_PROCESSING);
		
		userAccount.getChildren().add(logout);
		
		return userAccount;
	}
	
	/**/
	
	private class BusinessEntityInfosMenuItemComparator implements Comparator<BusinessEntityInfos>{

		@Override
		public int compare(BusinessEntityInfos o1, BusinessEntityInfos o2) {
			return o1.getClazz().getName().compareTo(o2.getClazz().getName());
		}
		
	}
	
}
