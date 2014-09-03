package org.cyk.ui.web.vaadin;

import java.io.Serializable;

import javax.inject.Singleton;

import lombok.extern.java.Log;

import org.cyk.ui.api.AbstractMessageManager;
import org.cyk.ui.api.MessageManager;

import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

@Singleton @Log
public class VaadinMessageManager extends AbstractMessageManager<VaadinNotificationMessage, Type> implements Serializable {

	private static final long serialVersionUID = -2135903644205681102L;
	
	@Override
	protected void initialisation() {
		MessageManager.INSTANCE = this;
		super.initialisation();
	}
	
	protected VaadinNotificationMessage buildMessage(Type severity, String summary, String details) {
		return new VaadinNotificationMessage(severity,summary, details);
	}
	
	@Override
	protected Type severityInfo() {
		return Type.HUMANIZED_MESSAGE;
	}
	
	@Override
	protected Type severityWarning() {
		return Type.WARNING_MESSAGE;
	}
	
	@Override
	protected Type severityError() {
		return Type.ERROR_MESSAGE;
	}
	
	@Override
	public void showInline() {
		log.warning("I dont know how to show message in inline!!!");
	}
	
	@Override
	public void showDialog() {
		Notification.show(builtMessage.getCaption(),builtMessage.getSeverity());  
	}

}
