package org.cyk.ui.web.primefaces.resources;

import java.io.Serializable;

import org.cyk.utility.common.userinterface.command.Menu;
import org.cyk.utility.common.userinterface.command.MenuNode;

public class MenuBuilder extends Menu.Builder.Adapter.Default implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected Menu __execute__() {
		Menu menu = super.__execute__();
		if(Menu.Type.MAIN.equals(menu.getType())){
			MenuNode menuNode = new MenuNode();
			menuNode.setLabelFromIdentifier("Main Item 1");
			menu.addOneChild(menuNode);
			
			menuNode = new MenuNode();
			menuNode.setLabelFromIdentifier("Main Item 2");
			menu.addOneChild(menuNode);
			
			menuNode = new MenuNode();
			menuNode.setLabelFromIdentifier("Main Item 3");
			menu.addOneChild(menuNode);	
		}else if(Menu.Type.CONTEXT.equals(menu.getType())){
			MenuNode menuNode1 = new MenuNode();
			menuNode1.setLabelFromIdentifier("Context Item 1");
			menu.addOneChild(menuNode1);
			
			MenuNode menuNode = new MenuNode();
			menuNode.setLabelFromIdentifier("Context Item 11");
			menuNode1.addOneChild(menuNode);
			
			MenuNode menuNode2 = new MenuNode();
			menuNode2.setLabelFromIdentifier("Context Item 2");
			menu.addOneChild(menuNode2);
			
			menuNode = new MenuNode();
			menuNode.setLabelFromIdentifier("Context Item 21");
			menuNode2.addOneChild(menuNode);
			
			menuNode = new MenuNode();
			menuNode.setLabelFromIdentifier("Context Item 22");
			menuNode2.addOneChild(menuNode);
		}
		
	
		return menu;
	}
	
}