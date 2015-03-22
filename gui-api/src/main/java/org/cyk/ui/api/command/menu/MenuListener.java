package org.cyk.ui.api.command.menu;

import org.cyk.ui.api.AbstractUserSession;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.menu.MenuManager.ModuleGroup;

public interface MenuListener {

	void moduleGroupCreated(AbstractUserSession userSession,ModuleGroup group,UICommandable commandable);
	
	//void systemMenu(UserSession userSession,SystemMenu systemMenu);

	void businessModuleGroupCreated(AbstractUserSession userSession,UICommandable commandableGroup);
	
	void applicationMenuCreated(AbstractUserSession userSession,UIMenu menu);
	
	void referenceEntityMenuCreated(AbstractUserSession userSession,UIMenu menu);

	void referenceEntityGroupCreated(AbstractUserSession userSession,UICommandable referenceEntityGroup);
	
	void calendarMenuCreated(AbstractUserSession userSession,UIMenu menu);

}
