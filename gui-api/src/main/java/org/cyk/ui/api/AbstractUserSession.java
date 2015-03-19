package org.cyk.ui.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import javax.enterprise.event.Observes;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.security.UserAccountBusiness;
import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.ui.api.command.menu.MenuManager;
import org.cyk.ui.api.command.menu.UIMenu;
import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractUserSession extends AbstractBean implements UserSession,Serializable {

	private static final long serialVersionUID = 958643519183802472L;

	@Inject protected UserAccountBusiness userAccountBusiness;
	protected UIMessageManager messageManager;
	
	@Getter @Setter protected Locale locale = Locale.FRENCH;
	@Getter @Setter protected UserAccount userAccount;
	@Getter @Setter protected UIMenu applicationMenu,referenceEntityMenu,securityMenu;
	@Getter protected final Collection<Notification> notifications = new ArrayList<Notification>();
	@Getter @Setter protected String notificationChannel;
	
	public void fired(@Observes Notification notification){
		System.out.println("AbstractUserSession.fired()");
		FacesMessage facesMessage = message(notification);
		if(facesMessage==null)
			return;
		publish("/"+UIManager.PUSH_NOTIFICATION_CHANNEL+"/"+ "*", facesMessage);
    }
	
	protected abstract void publish(String channel, FacesMessage facesMessage);

	protected FacesMessage message(Notification notification){
		String title=null,message=null;
		switch(notification.getRemoteEndPoint()){
		case MAIL_SERVER:
			//title=UIManager.getInstance().text("");
			break;
		case PHONE:
			
			break;
		case USER_INTERFACE:
			title = notification.getTitle();
			message = notification.getMessage();
			break;
		}
		if(title==null)
			return null;
		return new FacesMessage(title,message);
	}
	
	public Party getUser(){
		return userAccount == null?null:userAccount.getUser();
	}
	
	@Override
	public Boolean getLoggedIn() {
		return userAccount!=null;
	}
	
	public void init(UserAccount userAccount){
		setUserAccount(userAccount);
		setApplicationMenu(MenuManager.getInstance().applicationMenu(this));
		setReferenceEntityMenu(MenuManager.getInstance().referenceEntityMenu(this));
		setSecurityMenu(MenuManager.getInstance().securityMenu(this));
		
		notificationChannel = "/"+UIManager.PUSH_NOTIFICATION_CHANNEL+"/"+userAccount.getIdentifier();
	}
	
	public void doNotify(){
		System.out.println("AbstractUserSession.doNotify()");
		//debug(notification);
		//messageManager.notifications(notifications).showGrowl();
	}
	
	public void showNotifications(){
		System.out.println("AbstractUserSession.showNotifications()");
		Notification n = new Notification();
		n.setTitle("T3");
		n.setMessage("M3");
		notifications.add(n);
		
		n = new Notification();
		n.setTitle("T4");
		n.setMessage("M4");
		notifications.add(n);
		
		MessageManager.INSTANCE.notifications(notifications).showGrowl();
	}
	
	protected abstract void __logout__();
	
	public void logout(){
		userAccountBusiness.disconnect(getUserAccount());
		__logout__();
		__invalidateSession__();
		__navigateToPublicIndex__();
	}
	
	
	protected abstract void __navigateToPublicIndex__();
	
	protected abstract void __invalidateSession__();
}
