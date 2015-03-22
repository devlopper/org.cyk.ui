package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Singleton;

import org.cyk.ui.api.MessageManager;
import org.cyk.ui.web.api.WebUIMessageManager;
import org.primefaces.context.RequestContext;

@Singleton
public class PrimefacesMessageManager extends WebUIMessageManager implements Serializable {

	private static final long serialVersionUID = -2135903644205681102L;
	
	@Override
	protected void initialisation() {
		MessageManager.INSTANCE = this;
		super.initialisation();
	}
	
	@Override
	protected void __showDialog__() {
		for(FacesMessage facesMessage : builtMessages)
			RequestContext.getCurrentInstance().showMessageInDialog(facesMessage);  
	}
	
	@Override
	protected void __showGrowl__() {
		for(FacesMessage facesMessage : builtMessages)
			FacesContext.getCurrentInstance().addMessage(/*PrimefacesManager.getInstance().getNotificationChannelGrowlId()*/null,facesMessage);  
	}

}
