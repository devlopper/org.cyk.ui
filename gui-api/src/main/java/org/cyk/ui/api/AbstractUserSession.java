package org.cyk.ui.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.application.FacesMessage;
import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.RandomStringUtils;
import org.cyk.system.root.business.api.security.UserAccountBusiness;
import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.ui.api.command.menu.MenuManager;
import org.cyk.ui.api.command.menu.UIMenu;
import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractUserSession extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 958643519183802472L;

	private static final Map<String, AbstractUserSession> USER_SESSION_MAP = new HashMap<>();
	
	@Inject protected UserAccountBusiness userAccountBusiness;
	
	@Getter protected String identifier;
	protected Long timestamp;
	@Getter @Setter protected Locale locale = Locale.FRENCH;
	@Getter @Setter protected UserAccount userAccount;
	@Getter @Setter protected UIMenu applicationMenu,referenceEntityMenu,securityMenu;
	@Getter @Setter protected String notificationChannel;
	
	//FIXME not called. because of Session Scope ?
	/*
	public void notificationFired(@Observes Notification notification){
		System.out.println("AbstractUserSession.notificationFired()");
		/*FacesMessage facesMessage = message(notification);
		if(facesMessage==null)
			return;
		publish("/"+UIManager.PUSH_NOTIFICATION_CHANNEL+"/"+ "*", facesMessage);
    }
	*/
	
	public void notificationFired(Notification notification,FacesMessage facesMessage){
		userAccountBusiness.createSessionNotification(userAccount,notification);
		if(!userAccount.getSessionNotifications().isEmpty())
			__notificationFired__(notification,facesMessage);
	}
	
	protected void __notificationFired__(Notification notification,FacesMessage facesMessage){}
	
	public Party getUser(){
		return userAccount == null?null:userAccount.getUser();
	}
	
	public Boolean getLoggedIn() {
		return userAccount!=null;
	}
	
	public void init(UserAccount userAccount){
		setUserAccount(userAccount);
		setApplicationMenu(MenuManager.getInstance().applicationMenu(this));
		setReferenceEntityMenu(MenuManager.getInstance().referenceEntityMenu(this));
		setSecurityMenu(MenuManager.getInstance().securityMenu(this));
		
		identifier = userAccount.getIdentifier()+" - "+System.currentTimeMillis()+RandomStringUtils.randomAlphanumeric(10);
		notificationChannel = "/"+UIManager.PUSH_NOTIFICATION_CHANNEL+"/"+userAccount.getIdentifier();
		timestamp = System.currentTimeMillis();
		
		register(this);
	}
	
	public void showNotifications(){
		MessageManager.INSTANCE.notifications(userAccount.getSessionNotifications()).showGrowl();
	}
	
	protected abstract void __logout__();
	
	public void logout(){
		userAccountBusiness.disconnect(getUserAccount());
		__logout__();
		unRegister(this);
		__invalidateSession__();
		__navigateToPublicIndex__();
	}
	
	
	protected abstract void __navigateToPublicIndex__();
	
	protected abstract void __invalidateSession__();

	public abstract Boolean getIsAdministrator();

	public abstract Boolean getIsManager();
	
	public static void register(AbstractUserSession userSession){
		USER_SESSION_MAP.put(userSession.getIdentifier(), userSession);
	}
	
	public static void unRegister(AbstractUserSession userSession){
		USER_SESSION_MAP.remove(userSession.getIdentifier());
	}
	
	public static Collection<AbstractUserSession> find(UserAccount userAccount){
		Collection<AbstractUserSession> collection = new ArrayList<>();
		for(Entry<String, AbstractUserSession> entry : USER_SESSION_MAP.entrySet())
			if(entry.getValue().getUserAccount().equals(userAccount))
				collection.add(entry.getValue());
		return collection;
	}
}
