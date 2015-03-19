package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.cyk.ui.web.api.AbstractWebUserSession;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

@SessionScoped @Named
public class UserSession extends AbstractWebUserSession implements Serializable {

	private static final long serialVersionUID = -4310383407889787288L;

	private static final EventBus BUS = EventBusFactory.getDefault().eventBus();
	
	@Override
	protected void publish(String channel, FacesMessage facesMessage) {
		BUS.publish(channel, facesMessage);
	}

}
