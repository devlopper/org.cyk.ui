package org.cyk.ui.api;

import java.util.Locale;

import org.cyk.system.root.model.security.UserAccount;

public interface UserSession {

	UserAccount getUserAccount();
	void setUserAccount(UserAccount aUserAccount);
	
	Locale getLocale();
	void setLocale(Locale locale);
	
}
