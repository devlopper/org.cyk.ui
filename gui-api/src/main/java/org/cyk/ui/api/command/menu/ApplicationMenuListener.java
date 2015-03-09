package org.cyk.ui.api.command.menu;

import org.cyk.ui.api.UserSession;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.menu.ApplicationMenuManager.ModuleGroup;

public interface ApplicationMenuListener {

	void moduleGroupCreated(UserSession userSession,ModuleGroup group,UICommandable commandable);
	
	//void systemMenu(UserSession userSession,SystemMenu systemMenu);

	void businessModuleGroupCreated(UserSession userSession,UICommandable commandableGroup);
	
}
