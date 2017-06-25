package org.cyk.ui.web.primefaces;

import java.beans.Introspector;
import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.cyk.system.root.business.api.userinterface.UserInterfaceMenuNodeBusiness;
import org.cyk.system.root.model.userinterface.UserInterfaceCommand;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuItem;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuNode;
import org.cyk.ui.api.command.AbstractCommandableBuilder;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.menu.DefaultMenu;
import org.cyk.ui.api.command.menu.UIMenu;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuModel;

public class CommandableBuilder extends AbstractCommandableBuilder<Commandable> implements Serializable {

	private static final long serialVersionUID = -2137482834257337646L;

	public Commandable get(UserInterfaceCommand userInterfaceCommand){
		Commandable commandable = new Commandable();
		commandable.setLabel(userInterfaceCommand.getName());
		return commandable;
	}
	
	public Commandable get(UserInterfaceMenuNode userInterfaceMenuNode){
		Commandable root = get(userInterfaceMenuNode.getCommand());
		for(UserInterfaceMenuNode child : inject(UserInterfaceMenuNodeBusiness.class).findDirectChildrenByParent(userInterfaceMenuNode)){
			root.addChild(get(child));
		}
		return root;
	}
	
	public Commandable get(UserInterfaceMenuItem userInterfaceMenuItem){
		return get(userInterfaceMenuItem.getMenuNode());
	}
	
	public MenuModel menuModel(@NotNull UIMenu aMenu,Class<?> managedBeanClass,String fieldName){
		if(aMenu==null || aMenu.getCommandables()==null || aMenu.getCommandables().isEmpty())
			return null;
		MenuModel model = new DefaultMenuModel();
		for(UICommandable commandable : aMenu.getCommandables()){
			//model.addElement(menuItem(commandable,null, Introspector.decapitalize(managedBeanClass.getSimpleName()), fieldName));
			
			model.addElement(((Commandable)commandable).getComponent(MenuElement.class, new Object[]{null,Introspector.decapitalize(managedBeanClass.getSimpleName())
				, new String[]{fieldName}}));
		}
		return model;
	}
	
	public MenuModel menuModel(UserInterfaceMenuNode userInterfaceMenuNode,Class<?> managedBeanClass,String fieldName){
		UIMenu menu = new DefaultMenu();
		menu.addCommandable(get(userInterfaceMenuNode));
		return menuModel(menu, managedBeanClass, fieldName);
	}
	
}
