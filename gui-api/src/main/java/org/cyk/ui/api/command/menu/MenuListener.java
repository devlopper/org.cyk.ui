package org.cyk.ui.api.command.menu;

import org.cyk.ui.api.AbstractUserSession;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.menu.MenuManager.ModuleGroup;

public interface MenuListener<USER_SESSION extends AbstractUserSession<?, ?>> {

	Boolean moduleGroupCreateable(USER_SESSION userSession,ModuleGroup group);
	void moduleGroupCreated(USER_SESSION userSession,ModuleGroup group,UICommandable commandable);
	
	//void systemMenu(UserSession userSession,SystemMenu systemMenu);

	void businessModuleGroupCreated(USER_SESSION userSession,UICommandable commandableGroup);
	
	void applicationMenuCreated(USER_SESSION userSession,UIMenu menu);
	
	void referenceEntityMenuCreated(USER_SESSION userSession,UIMenu menu);

	void referenceEntityGroupCreated(USER_SESSION userSession,UICommandable referenceEntityGroup);
	
	void calendarMenuCreated(USER_SESSION userSession,UIMenu menu);

	void sessionContextualMenuCreated(USER_SESSION userSession,UIMenu menu);

}
