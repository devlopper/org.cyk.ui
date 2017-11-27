package org.cyk.ui.api.resources;

import java.io.Serializable;

import org.cyk.ui.api.resources.window.IdentifiablesManageWindow;
import org.cyk.utility.common.userinterface.command.Menu;
import org.cyk.utility.common.userinterface.command.MenuNode;

public class MenuBuilder extends Menu.Builder.Adapter.Default implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected MenuNode homeMainMenuNode,controlPanelMainMenuNode;
	
	@Override
	protected Menu __execute__() {
		Menu menu = super.__execute__();
		
		if(Menu.Type.MAIN.equals(menu.getType())){
			homeMainMenuNode = menu.addNode("ui.menu.home","homeView");
		
			controlPanelMainMenuNode = menu.addNode("ui.menu.controlpanel");
			controlPanelMainMenuNode.addNode("ui.menu.controlpanel.identifiables.manage","controlPanelIdentifiablesManageView");
			
			/*
			menu.addNode("ui.menu.tools","toolsView")
				.addNode("ui.menu.tools.data.export")
				.getParentAsNode().addNode("ui.menu.tools.data.import")
				;
			*/
			/*
			menu.addNode("ui.menu.user","userView")
				.addNode("ui.menu.user.account.manage")
				.getParentAsNode().addNode("ui.menu.user.disconnect")
				;
			*/
		}else if(Menu.Type.CONTEXT.equals(menu.getType())){
			if(componentParent instanceof IdentifiablesManageWindow){
				addNodeIdentifiablesManage(menu);
			}
		}
		return menu;
	}
	
	
	protected void addNodeIdentifiablesManage(Menu menu){}
	
}