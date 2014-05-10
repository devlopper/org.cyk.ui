package org.cyk.ui.web.primefaces;

import java.beans.Introspector;
import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.UICommand;
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

	private static final String EL_MENU_ITEM_COMMAND_FORMAT = "#"+"{"+ "%s.%s.commandable('%s').command.execute(null)}";
	
	public Object build(UICommand aCommand){
		CommandButton command = new CommandButton();
		
		return command;
	}
	
	public MenuElement menuItem(UIManager uiManager,WebManager webManager,UICommandable aCommandable,DefaultSubMenu parent,String managedBeanName,@NotNull String...fields){
		if(aCommandable.getChildren().isEmpty()){
			DefaultMenuItem	menuItem = new DefaultMenuItem();
			menuItem.setValue(aCommandable.getLabel());
			if(StringUtils.isNotEmpty(aCommandable.getIcon()))
				menuItem.setIcon(aCommandable.getIcon());
			if(aCommandable.getIsNavigationCommand()){
				if(aCommandable.getDynamicView()==null){
					
				}else{
					switch(aCommandable.getDynamicView()){
					case EDITOR:menuItem.setOutcome("dynamiceditor");break;
					case TABLE:menuItem.setOutcome("dynamictable");break;
					}
					menuItem.setParam(webManager.getRequestParameterClass(), uiManager.keyFromClass(aCommandable.getDynamicClass()));
				}
				
			}else{
				menuItem.setUpdate(":form:contentPanel");
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
				menuItem(uiManager,webManager,commandable, subMenu, managedBeanName, fields);
			return subMenu;
		}
	}
	
	public MenuModel menuModel(UIManager uiManager,WebManager webManager,@NotNull UIMenu aMenu,Class<?> managedBeanClass,String fieldName){
		if(aMenu==null || aMenu.getCommandables()==null || aMenu.getCommandables().isEmpty())
			return null;
		MenuModel model = new DefaultMenuModel();
		for(UICommandable commandable : aMenu.getCommandables()){
			model.addElement(menuItem(uiManager,webManager,commandable,null, Introspector.decapitalize(managedBeanClass.getSimpleName()), fieldName));
		}
		return model;
	}
	
}
