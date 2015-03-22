package org.cyk.ui.web.primefaces.push;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.event.Observes;
import javax.faces.application.FacesMessage;

import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.ui.api.AbstractUserSession;
import org.cyk.ui.api.UIManager;

public abstract class AbstractNotificationPushEndPoint extends AbstractPushEndPoint<FacesMessage> implements Serializable {

	private static final long serialVersionUID = 843248854298599261L;

	public void notificationFired(@Observes Notification notification){
		FacesMessage facesMessage = message(notification);
		if(facesMessage==null)
			return;
		if(Boolean.TRUE.equals(notification.getAll()))
			bus.publish("/"+UIManager.PUSH_NOTIFICATION_CHANNEL+"/*", facesMessage);
		else{
			for(UserAccount userAccount : notification.getUserAccounts()){
				Collection<AbstractUserSession> userSessions = AbstractUserSession.find(userAccount);
				if(userSessions.size()>1)
					System.out.println("User sessions found : "+userSessions.size());
				for(AbstractUserSession userSession : userSessions)
					userSession.notificationFired(notification, facesMessage);
			}
		}
    }
	
	/**/
	
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
	
}
