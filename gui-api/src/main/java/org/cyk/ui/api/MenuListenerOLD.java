package org.cyk.ui.api;

import java.util.Collection;

import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.menu.UIMenu;

@Deprecated
public interface MenuListenerOLD {

	public enum ModuleType{BUSINESS,CONTROL_PANEL,HELP,USER_ACCOUNT}
	
	void menu(UserSession userSession,UIMenu menu,MenuManagerOLD.Type type);

	UICommandable module(UserSession userSession,UIMenu menu,UICommandable module,ModuleType type);

	Collection<UICommandable> modules(UserSession userSession, UIMenu aMenu,Collection<UICommandable> modules, ModuleType type);
}