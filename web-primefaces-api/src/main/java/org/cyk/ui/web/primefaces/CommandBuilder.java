package org.cyk.ui.web.primefaces;

import java.beans.Introspector;
import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UIMenu;
import org.cyk.ui.web.api.WebManager;
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
	
	public CommandButton commandButton(UICommandable aCommandable){
		CommandButton commandButton = new CommandButton();
		commandButton.setValue(aCommandable.getLabel());
		commandButton.setUpdate(":form:contentPanel");
		if(StringUtils.isNotEmpty(aCommandable.getIcon()))
			commandButton.setIcon(aCommandable.getIcon());
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
			if(StringUtils.isNotEmpty(aCommandable.getIcon()))
				menuItem.setIcon(aCommandable.getIcon());
			if(aCommandable.getIsNavigationCommand()){
				if(aCommandable.getViewType()==null){
					
				}else{
					switch(aCommandable.getViewType()){
					case DYNAMIC_EDITOR:menuItem.setOutcome("dynamiceditor");break;
					case DYNAMIC_TABLE:menuItem.setOutcome("dynamictable");break;
					case MANAGEMENT_DEPLOYMENT:menuItem.setOutcome("deploymentmanagement");break;
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
				
				menuItem.setCommand(String.format(EL_MENU_ITEM_COMMAND_FORMAT, managedBeanName,StringUtils.join(fields,"."),aCommandable.getIdentifier())); 
				if(UICommandable.ProcessGroup.THIS.equals(aCommandable.getProcessGroup()))
					menuItem.setProcess("@this");	
			}
			
			
			if(parent!=null)
				parent.addElement(menuItem);
			return menuItem;
		}else{
			DefaultSubMenu subMenu = new DefaultSubMenu(aCommandable.getLabel());
			if(StringUtils.isNotEmpty(aCommandable.getIcon()))
				subMenu.setIcon(aCommandable.getIcon());
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
	
}
