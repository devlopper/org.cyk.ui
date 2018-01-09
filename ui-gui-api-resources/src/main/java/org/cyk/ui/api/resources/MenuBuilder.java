package org.cyk.ui.api.resources;

import java.io.Serializable;

import org.cyk.ui.api.resources.window.IdentifiablesManageWindow;
import org.cyk.utility.common.security.SecurityHelper;
import org.cyk.utility.common.userinterface.command.Menu;
import org.cyk.utility.common.userinterface.command.MenuNode;

public class MenuBuilder extends Menu.Builder.Adapter.Default implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected MenuNode homeMainMenuNode,controlPanelMainMenuNode;
	
	@Override
	protected Menu __execute__() {
		Menu menu = super.__execute__();
		Object principal = SecurityHelper.getInstance().getPrincipal();
		if(Menu.Type.MAIN.equals(menu.getType())){
			homeMainMenuNode = menu.addNode("userinterface.menu.main.home.label","homeView");
		
			controlPanelMainMenuNode = menu.addNode("userinterface.menu.main.controlpanel.label");
			controlPanelMainMenuNode.addNode("userinterface.menu.main.controlpanel.identifiables.manage.label","controlPanelIdentifiablesManageView");
			
			/*
			menu.addNode("ui.menu.tools","toolsView")
				.addNode("ui.menu.tools.data.export")
				.getParentAsNode().addNode("ui.menu.tools.data.import")
				;
			*/
			
			if(principal!=null){
				MenuNode menuNode = menu.addNode((String)null,"userView");
				menuNode.getLabel().getPropertiesMap().setValue(principal);
				menuNode.addNode("userinterface.menu.main.user.account.manage.label");
				menuNode.addNode("userinterface.menu.main.user.logout.label","userLogoutView");
			}
			
			
		}else if(Menu.Type.CONTEXT.equals(menu.getType())){
			if(componentParent instanceof IdentifiablesManageWindow){
				addNodeIdentifiablesManage(menu);
			}
		}
		return menu;
	}
	
	
	protected void addNodeIdentifiablesManage(Menu menu){}
	
}