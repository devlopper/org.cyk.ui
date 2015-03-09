package org.cyk.ui.web.api;

import java.io.Serializable;

import org.cyk.ui.api.UserSession;
import org.cyk.ui.api.command.menu.SystemMenu;
import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractWebManager extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 406884223652214395L;

	public abstract SystemMenu systemMenu(UserSession userSession);
	
}
