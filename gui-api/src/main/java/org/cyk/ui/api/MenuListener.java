package org.cyk.ui.api;

import org.cyk.system.root.model.party.Party;
import org.cyk.ui.api.command.UIMenu;

public interface MenuListener {

	void menu(UIMenu menu,MenuManager.Type type,Party user);
	
}