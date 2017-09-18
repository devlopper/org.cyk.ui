package org.cyk.ui.web.api;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.NotificationHelper.Notification;

public class NotificationHelper extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	public static class Viewer extends org.cyk.ui.api.NotificationHelper.Viewer implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@Override
		protected void __execute__(Notification notification, Type type) {
			FacesContext.getCurrentInstance().addMessage(null,createMessage(notification));//client id should be provided for growl
		}
		
		protected FacesMessage createMessage(Notification notification){
			FacesMessage.Severity severity = null;
			if(org.cyk.utility.common.helper.NotificationHelper.SeverityType.INFO.equals(notification.getSeverityType()))
				severity = FacesMessage.SEVERITY_INFO;
			else if(org.cyk.utility.common.helper.NotificationHelper.SeverityType.WARNING.equals(notification.getSeverityType()))
				severity = FacesMessage.SEVERITY_WARN;
			else if(org.cyk.utility.common.helper.NotificationHelper.SeverityType.ERROR.equals(notification.getSeverityType()))
				severity = FacesMessage.SEVERITY_ERROR;
			return new FacesMessage(severity, notification.getSummary(), notification.getDetails());
		}
		
	}
	
}