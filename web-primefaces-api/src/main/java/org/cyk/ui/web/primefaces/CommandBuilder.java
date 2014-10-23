package org.cyk.ui.web.primefaces;

import java.beans.Introspector;
import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.api.command.UIMenu;
import org.cyk.ui.web.api.WebManager;
import org.cyk.ui.web.api.WebNavigationManager;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuModel;

public class CommandBuilder implements Serializable {

	private static final long serialVersionUID = 6706193290921067166L;
	private static final CommandBuilder INSTANCE = new CommandBuilder();
	private static final String EL_MENU_ITEM_COMMAND_FORMAT = "#"+"{"+ "%s.%s.commandable('%s').command.execute(null)}";
	
	public static CommandBuilder getInstance() {
		return INSTANCE;
	}
	
	public CommandButton commandButton(Commandable aCommandable){
		CommandButton commandButton = new CommandButton();
		commandButton.setRendered(Boolean.TRUE.equals(aCommandable.getRendered()));
		if(Boolean.TRUE.equals(aCommandable.getShowLabel()))
			commandButton.setValue(aCommandable.getLabel());
		commandButton.setUpdate(aCommandable.getUpdate());
		if(aCommandable.getIconType()!=null)
			commandButton.setIcon(icon(aCommandable.getIconType()));
		if(StringUtils.isEmpty(aCommandable.getTooltip()))
			commandButton.setTitle(aCommandable.getLabel());
		else
			commandButton.setTitle(aCommandable.getTooltip());
		if(UICommandable.ProcessGroup.THIS.equals(aCommandable.getProcessGroup()))
			commandButton.setProcess("@this");		
		/*
		ConfirmBehavior confirmBehavior = new ConfirmBehavior();
		confirmBehavior.setHeader("Hello Titke");
		confirmBehavior.setMessage("Message");
		commandButton.addClientBehavior("org.primefaces.behavior.ConfirmBehavior", confirmBehavior);
		*/
		return commandButton;
	}
	
	public MenuElement menuItem(UICommandable aCommandable,DefaultSubMenu parent,String managedBeanName,@NotNull String...fields){
		if(aCommandable.getChildren().isEmpty()){
			DefaultMenuItem	menuItem = new DefaultMenuItem();
			menuItem.setValue(aCommandable.getLabel());
			if(aCommandable.getIconType()!=null)
				menuItem.setIcon(icon(aCommandable.getIconType()));
			if(aCommandable.getIsNavigationCommand()){
				if(aCommandable.getViewType()==null){
					if(aCommandable.getViewId()!=null)
						menuItem.setOutcome(aCommandable.getViewId().toString());
				}else{
					switch(aCommandable.getViewType()){
					case DYNAMIC_CRUD_ONE:
						menuItem.setOutcome(WebNavigationManager.getInstance().getOutcomeDynamicCrudOne());
						menuItem.setParam(UIManager.getInstance().getCrudParameter(), UIManager.getInstance().getCrudCreateParameter());
						break;
					case DYNAMIC_CRUD_MANY:menuItem.setOutcome(WebNavigationManager.getInstance().getOutcomeDynamicCrudMany());break;
					case USERACCOUNT_LOGOUT:menuItem.setOutcome(WebNavigationManager.getInstance().getOutcomeLogout());break;
					case MANAGEMENT_DEPLOYMENT:menuItem.setOutcome(WebNavigationManager.getInstance().getOutcomeDeploymentManagement());break;
					case TOOLS_CALENDAR:menuItem.setOutcome(WebNavigationManager.getInstance().getOutcomeToolsCalendar());break;
					default:break;
					}
					if(aCommandable.getBusinessEntityInfos()!=null)
						menuItem.setParam(WebManager.getInstance().getRequestParameterClass(), UIManager.getInstance().keyFromClass(aCommandable.getBusinessEntityInfos()));
				}
				
			}else{
				
				menuItem.setUpdate(":form:contentPanel");
				menuItem.setOnstart(WebManager.getInstance().getBlockUIDialogWidgetId()+".show();");
				menuItem.setOnsuccess(WebManager.getInstance().getBlockUIDialogWidgetId()+".hide();");
				menuItem.setGlobal(true);
				
				if(aCommandable.getViewType()==null){
					
				}else{
					switch(aCommandable.getViewType()){
					case USERACCOUNT_LOGOUT:
						menuItem.setCommand("#{webSession.logout()}");
						menuItem.setAjax(Boolean.FALSE);
						break;
					default:menuItem.setCommand(String.format(EL_MENU_ITEM_COMMAND_FORMAT, managedBeanName,StringUtils.join(fields,"."),aCommandable.getIdentifier())); 
					}
				}
				
				
				if(UICommandable.ProcessGroup.THIS.equals(aCommandable.getProcessGroup()))
					menuItem.setProcess("@this");	
			}
			
			
			if(parent!=null)
				parent.addElement(menuItem);
			return menuItem;
		}else{
			DefaultSubMenu subMenu = new DefaultSubMenu(aCommandable.getLabel());
			if(aCommandable.getIconType()!=null)
				subMenu.setIcon(icon(aCommandable.getIconType()));
			for(UICommandable commandable : aCommandable.getChildren())
				menuItem(commandable, subMenu, managedBeanName, fields);
			return subMenu;
		}
	}
	
	public MenuModel menuModel(@NotNull UIMenu aMenu,Class<?> managedBeanClass,String fieldName){
		if(aMenu==null || aMenu.getCommandables()==null || aMenu.getCommandables().isEmpty())
			return null;
		MenuModel model = new DefaultMenuModel();
		for(UICommandable commandable : aMenu.getCommandables()){
			model.addElement(menuItem(commandable,null, Introspector.decapitalize(managedBeanClass.getSimpleName()), fieldName));
		}
		return model;
	}
	
	private String icon(IconType iconType){
		if(iconType==null)
			return null;
		switch(iconType){
		case ACTION_ADD:return "ui-icon-plus";
		case ACTION_CANCEL:return "ui-icon-close";
		case ACTION_OPEN:return "ui-icon-folder-open";
		case ACTION_REMOVE:return "ui-icon-trash";
		case ACTION_ADMINISTRATE:return "ui-icon-gear";
		case ACTION_HELP:return "ui-icon-help";
		case ACTION_APPLY:return "ui-icon-check";
		case ACTION_EDIT:return "ui-icon-pencil";
		case ACTION_GO_BACK:return "ui-icon-arrow-e";
		case ACTION_OK:return "ui-icon-check";
		case ACTION_SAVE:return "ui-icon-check";
		case ACTION_SEARCH:return "ui-icon-search";
		case ACTION_PREVIEW:return "ui-icon-image";
		default:return null;
		}
	}
	
}
