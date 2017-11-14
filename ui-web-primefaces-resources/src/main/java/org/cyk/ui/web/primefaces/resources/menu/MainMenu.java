package org.cyk.ui.web.primefaces.resources.menu;

import java.io.Serializable;

import org.cyk.utility.common.userinterface.command.Menu;
import org.cyk.utility.common.userinterface.command.MenuNode;

public class MainMenu extends Menu implements Serializable {
	private static final long serialVersionUID = 1L;

	public MainMenu() {
		setRenderType(Menu.RenderType.BAR);
		MenuNode menuNode = new MenuNode();
		menuNode.setLabelFromIdentifier("L1");
		addOneChild(menuNode);
		
		menuNode = new MenuNode();
		menuNode.setLabelFromIdentifier("L2");
		addOneChild(menuNode);
		
		MenuNode menuNode3 = new MenuNode();
		menuNode3.setLabelFromIdentifier("L3");
		addOneChild(menuNode3);
		menuNode = new MenuNode();
		menuNode.setLabelFromIdentifier("L31");
		menuNode3.addOneChild(menuNode);
		menuNode = new MenuNode();
		menuNode.setLabelFromIdentifier("L32");
		menuNode3.addOneChild(menuNode);
		MenuNode menuNode33 = new MenuNode();
		menuNode33.setLabelFromIdentifier("L33");
		menuNode3.addOneChild(menuNode33);
		menuNode = new MenuNode();
		menuNode.setLabelFromIdentifier("L331");
		menuNode33.addOneChild(menuNode);
		menuNode = new MenuNode();
		menuNode.setLabelFromIdentifier("L332_Google");
		menuNode.getPropertiesMap().setUrl("http://www.google.com");
		menuNode33.addOneChild(menuNode);
		menuNode = new MenuNode();
		menuNode.setLabelFromIdentifier("L333");
		menuNode33.addOneChild(menuNode);
		menuNode = new MenuNode();
		menuNode.setLabelFromIdentifier("L334");
		menuNode33.addOneChild(menuNode);
		
		menuNode = new MenuNode();
		menuNode.setLabelFromIdentifier("L4");
		addOneChild(menuNode);
		
		menuNode = new MenuNode();
		menuNode.setLabelFromIdentifier("L5");
		addOneChild(menuNode);
	}
	
}
