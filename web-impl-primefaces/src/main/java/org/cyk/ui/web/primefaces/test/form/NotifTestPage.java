package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.event.NotificationBusiness;
import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.event.Notification.RemoteEndPoint;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;

@Named @RequestScoped @Getter @Setter
public class NotifTestPage extends AbstractPrimefacesPage implements Serializable {
      
	private static final long serialVersionUID = 1659264103742702189L;
	
	@Inject private NotificationBusiness notificationBusiness;
	
	private String title="LeTitre",message="Ligne1\r\nLigne 2\r\nLigne three";
	
    public void send() {
    	Notification notification = new Notification();
    	notification.setRemoteEndPoint(RemoteEndPoint.USER_INTERFACE);
		notification.setTitle(title);
		notification.setMessage(message);
		notificationBusiness.notify(notification, "kycdev@gmail.com", null);
    }
}