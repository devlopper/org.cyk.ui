package org.cyk.ui.api;

public interface UserSessionListener {

	void login(UserSession userSession);
	
	void logout(UserSession userSession);

}
