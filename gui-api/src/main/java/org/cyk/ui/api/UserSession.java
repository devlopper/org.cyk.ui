package org.cyk.ui.api;

import java.util.Collection;
import java.util.Locale;

import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.ui.api.command.menu.UIMenu;

public interface UserSession {

	UserAccount getUserAccount();
	void setUserAccount(UserAccount aUserAccount);
	
	Locale getLocale();
	void setLocale(Locale locale);
	
	Boolean getLoggedIn();
	
	Boolean getIsAdministrator();
	
	Boolean getIsManager();
	
	UIMenu getApplicationMenu();
	UIMenu getReferenceEntityMenu();
	
	String getNotificationChannel();
	void setNotificationChannel(String channel);
	Collection<Notification> getNotifications();
	
	void logout();
	
}
