package org.cyk.ui.api.resources;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.ui.api.resources.window.IdentifiableConsultWindow;
import org.cyk.ui.api.resources.window.IdentifiablesManageWindow;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.security.SecurityHelper;
import org.cyk.utility.common.userinterface.command.Menu;
import org.cyk.utility.common.userinterface.command.MenuNode;
import org.cyk.utility.common.userinterface.container.window.Window;

public class MenuBuilder extends Menu.Builder.Adapter.Default implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/*
	@Override
	protected Menu __execute__() {
		Menu menu = super.__execute__();
		Object principal = SecurityHelper.getInstance().getPrincipal();
		if(Menu.Type.MAIN.equals(menu.getType())){
			homeMainMenuNode = menu.addNode("userinterface.menu.main.home.label","homeView");
		
			controlPanelMainMenuNode = menu.addNode("userinterface.menu.main.controlpanel.label");
			controlPanelMainMenuNode.addNode("userinterface.menu.main.controlpanel.identifiables.manage.label","controlPanelIdentifiablesManageView");
			
			if(principal!=null){
				MenuNode menuNode = menu.addNode((String)null,"userView");
				menuNode.getLabel().getPropertiesMap().setValue(principal);
				menuNode.addNode("userinterface.menu.main.user.account.manage.label");
				menuNode.addNode("userinterface.menu.main.user.logout.label","userLogoutView");
			}
			
			
		}else if(Menu.Type.CONTEXT.equals(menu.getType())){
			if(componentParent instanceof IdentifiablesManageWindow){
				addNodeIdentifiablesManage(menu);
			}else if(componentParent instanceof IdentifiableConsultWindow){
				addNodeInstance(menu,((Window)componentParent).getActionOnClassInstances().iterator().next(),(Constant.Action) componentParent.getPropertiesMap().getAction());
			}
		}
		return menu;
	}*/
	/*
	protected void addNodeInstance(Menu menu,Object instance,Constant.Action action){
		MenuNode node = menu.addNode("");
		node.getPropertiesMap().setExpanded(Boolean.TRUE);
		node._setLabelPropertyValue(InstanceHelper.getInstance().getLabel(instance));
		node.addNodeActionUpdate(instance);
		if(!(instance instanceof GlobalIdentifier))
			node.addNodeActionDelete(instance);
		if(instance instanceof AbstractIdentifiable)
			node.addNodeActionRead( ((AbstractIdentifiable)instance).getGlobalIdentifier());
		
		if(!(instance instanceof GlobalIdentifier)){
			node = menu.addNode("");
			node.getPropertiesMap().setExpanded(Boolean.TRUE);
			node._setLabelPropertyValue(StringHelper.getInstance().getClazz(instance.getClass()));
			node.addNodeActionListMany(instance.getClass());	
		}
		
		
	}*/
	
}