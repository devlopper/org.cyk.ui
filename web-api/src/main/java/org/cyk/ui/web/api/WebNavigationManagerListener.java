package org.cyk.ui.web.api;

import org.cyk.ui.api.AbstractUserSession;

public interface WebNavigationManagerListener {

	String homeUrl(AbstractUserSession userSession);
	
}
