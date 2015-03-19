package org.cyk.ui.web.api;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Singleton;

import lombok.extern.java.Log;

import org.cyk.ui.api.AbstractMessageManager;
import org.cyk.ui.api.UIMessageManager;

@Singleton @Log
public class WebUIMessageManager extends AbstractMessageManager<FacesMessage, Severity> implements UIMessageManager,Serializable {
	
	private static final long serialVersionUID = -2096649010369789825L;
	
	@Override
	protected FacesMessage buildMessage(Severity severity, String summary, String details) {
		return new FacesMessage(severity,summary, details);
	}
	
	@Override
	protected Severity severityInfo() {
		return FacesMessage.SEVERITY_INFO;
	}
	
	@Override
	protected Severity severityWarning() {
		return FacesMessage.SEVERITY_WARN;
	}
	
	@Override
	protected Severity severityError() {
		return FacesMessage.SEVERITY_ERROR;
	}
	
	@Override
	protected void __showInline__() {
		for(FacesMessage facesMessage : builtMessages)
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}

	@Override
	protected void __showDialog__() {
		log.warning("I dont know how to show message in dialog!!!");
	}

	@Override
	protected void __showGrowl__() {
		log.warning("I dont know how to show message in growl!!!");
	}
	
	/**/

}
