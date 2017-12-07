package org.cyk.ui.web.primefaces.resources;

import java.io.Serializable;

import org.cyk.utility.common.userinterface.command.Menu;
import org.cyk.utility.common.userinterface.command.MenuNode;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.Submenu;

public class MenuBasedOnMenuModel extends Menu.Builder.Target.Adapter.Default<DefaultMenuModel> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected Object createNotLeaf(DefaultMenuModel menu, MenuNode menuNode) {
		DefaultSubMenu subMenu = new DefaultSubMenu();
		subMenu.setLabel((String)menuNode.getLabel().getPropertiesMap().getValue());
		return subMenu;
	}
	
	@Override
	protected Object createLeaf(DefaultMenuModel menu, MenuNode menuNode) {
		DefaultMenuItem	menuItem = new DefaultMenuItem();
		if(Boolean.TRUE.equals(menuNode.getLabel().getPropertiesMap().getRendered()))
			menuItem.setValue(menuNode.getLabel().getPropertiesMap().getValue());
		if(menuNode.getPropertiesMap().getIcon()!=null)
			menuItem.setIcon((String)menuNode.getPropertiesMap().getIcon());
		if(menuNode.getPropertiesMap().getUrl()!=null)
			menuItem.setUrl((String)menuNode.getPropertiesMap().getUrl());
		if(menuNode.getPropertiesMap().getOutcome()!=null)
			menuItem.setOutcome((String)menuNode.getPropertiesMap().getOutcome());
		
		if(menuNode.getPropertiesMap().getTitle()!=null)
			menuItem.setTitle((String)menuNode.getPropertiesMap().getTitle());
		if(menuNode.getPropertiesMap().getTitle()==null)
			menuItem.setTitle((String)menuNode.getLabel().getPropertiesMap().getValue());
		
		return menuItem;
	}
		
	@Override
	protected void addNode(DefaultMenuModel menu, Object node, Object parent) {
		if(parent==null)
			menu.addElement((MenuElement) node);
		else if(parent instanceof Submenu)
			((DefaultSubMenu)parent).addElement((MenuElement) node);
	}
	
	@Override
	protected DefaultMenuModel __execute__() {
		getInput().getPropertiesMap().setTemplate(PrimefacesResourcesManager.getInstance().getMenuTemplate(getInput()));
		return super.__execute__();
	}
}