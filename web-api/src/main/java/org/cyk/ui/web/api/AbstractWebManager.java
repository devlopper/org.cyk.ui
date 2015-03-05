package org.cyk.ui.web.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.UIProvider;
import org.cyk.ui.api.UserSession;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractWebManager extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 406884223652214395L;

	/**/
	
	public Collection<UICommandable> menuItemModules(UserSession userSession){
		Collection<UICommandable> modules = new ArrayList<>();
		modules.addAll(menuItemBusinessModules(userSession));
		modules.add(menuItemControlPanelModule(userSession));
		modules.add(menuItemUserAccountModule(userSession));
		modules.add(menuItemHelpModule(userSession));
		return modules;
	}
	
	public Collection<UICommandable> menuItemBusinessModules(UserSession userSession){
		Collection<UICommandable> items = new ArrayList<>();
		
		return items;
	}
	
	public UICommandable menuItemControlPanelModule(UserSession userSession){
		UICommandable module = UIProvider.getInstance().createCommandable("command.controlpanel", null);
		
		return module;
	}
	
	public UICommandable menuItemUserAccountModule(UserSession userSession){
		UICommandable module = UIProvider.getInstance().createCommandable("command.account", null);
		
		return module;
	}
	
	public UICommandable menuItemHelpModule(UserSession userSession){
		UICommandable module = UIProvider.getInstance().createCommandable("command.help", null);
		
		return module;
	}
	
	/**/
	
	public abstract Collection<UICommandable> businessMenuItems(UserSession userSession);
	
	public abstract Collection<Class<? extends AbstractIdentifiable>> parameterMenuItemClasses(UserSession userSession);
	
}
