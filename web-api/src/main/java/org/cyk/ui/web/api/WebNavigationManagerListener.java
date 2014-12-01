package org.cyk.ui.web.api;

import org.cyk.ui.api.UserSession;

public interface WebNavigationManagerListener {

	String homeUrl(UserSession userSession);
	
}
