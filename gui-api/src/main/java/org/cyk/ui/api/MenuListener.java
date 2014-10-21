package org.cyk.ui.api;

import org.cyk.ui.api.command.UIMenu;

public interface MenuListener {

	void menu(UserSession userSession,UIMenu menu,MenuManager.Type type);
	
}