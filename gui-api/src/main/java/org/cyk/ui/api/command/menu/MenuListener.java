package org.cyk.ui.api.command.menu;

import org.cyk.ui.api.UserSession;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.menu.MenuManager.ModuleGroup;

public interface MenuListener {

	void moduleGroupCreated(UserSession userSession,ModuleGroup group,UICommandable commandable);
	
	//void systemMenu(UserSession userSession,SystemMenu systemMenu);

	void businessModuleGroupCreated(UserSession userSession,UICommandable commandableGroup);
	
	void applicationMenuCreated(UserSession userSession,UIMenu menu);
	
	void referenceEntityMenuCreated(UserSession userSession,UIMenu menu);

	void referenceEntityGroupCreated(UserSession userSession,UICommandable referenceEntityGroup);
	
	void calendarMenuCreated(UserSession userSession,UIMenu menu);

}
