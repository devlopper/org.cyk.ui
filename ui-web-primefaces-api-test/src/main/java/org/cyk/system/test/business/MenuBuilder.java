package org.cyk.system.test.business;

import java.io.Serializable;

import org.cyk.utility.common.userinterface.command.Menu;

public class MenuBuilder extends org.cyk.ui.web.primefaces.resources.MenuBuilder implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected Menu __execute__() {
		Menu menu = super.__execute__();
		
		
		
		return menu;
	}
	
	@Override
	protected void addNodeIdentifiablesManage(Menu menu) {
		super.addNodeIdentifiablesManage(menu);
		
		menu.addNode("Custom").addNode("List of Transfer", "transferlist01");
	}
	
}