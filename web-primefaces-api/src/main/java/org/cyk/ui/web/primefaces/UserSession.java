package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.event.Notification;
import org.cyk.ui.web.api.AbstractWebUserSession;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

@SessionScoped @Named
public class UserSession extends AbstractWebUserSession implements Serializable {

	private static final long serialVersionUID = -4310383407889787288L;

	private static final EventBus BUS = EventBusFactory.getDefault().eventBus();
	 
	@Override
	protected void __notificationFired__(Notification notification,FacesMessage facesMessage) {
		super.__notificationFired__(notification, facesMessage);
		BUS.publish(notificationChannel, facesMessage);
	}
	 
	@Override
	public void showNotifications() {
		super.showNotifications();
		if(StringUtils.endsWith(navigationManager.getRequestUrl(), "/private/__tools__/event/notifications.jsf")){
			//navigationManager.redirectTo(navigationManager.getOutcomeNotifications());
			//RequestContext.getCurrentInstance().execute("updateNotifications();");
		}
	}

}
