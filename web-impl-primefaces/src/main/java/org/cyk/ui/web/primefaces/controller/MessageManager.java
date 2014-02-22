package org.cyk.ui.web.primefaces.controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.cyk.ui.api.IMessageManager;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.cdi.provider.CommonMethodProvider;
import org.cyk.utility.common.cdi.provider.TextServiceFindMethod;

@Singleton
public class MessageManager extends AbstractBean implements IMessageManager,Serializable {
	
	private static final long serialVersionUID = -2096649010369789825L;
	
	@Inject private CommonMethodProvider commonMethodProvider;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		commonMethodProvider.setTextServiceFindMethod(new TextServiceFindMethod() {
			private static final long serialVersionUID = -2548338197286306669L;
			@Override
			public String execute(String id, Object[] parameters, Boolean isId) {
				return id;
			}
		});
	}
	
	public void add(Severity severity,Object text,Boolean isMessageId){
		String message = commonMethodProvider.getTextServiceFindMethod().execute(text.toString(),null,isMessageId);
		message = StringUtils.replace(message, "\r\n", "<br/>");
		message = StringUtils.replace(message, "\n", "<br/>");
		//message = StringEscapeUtils.escapeHtml4(message);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity,message, message));
	}
	
	public void add(Severity severity,Object messageId){
		add(severity, messageId, Boolean.TRUE);
	}
	
	public void addError(Object text,Boolean isMessageId){
		add(FacesMessage.SEVERITY_ERROR, text,isMessageId);
	}
	
	public void addError(Object text){
		addError(text, Boolean.TRUE);
	}
	
	public void addError(Exception exception){
		add(FacesMessage.SEVERITY_ERROR, StringUtils.isEmpty(exception.getMessage())?exception.toString():exception.getMessage() ,Boolean.FALSE);
	}
	
	public void addInfo(Object text,Boolean isMessageId){
		add(FacesMessage.SEVERITY_INFO, text,isMessageId);
	}
	
	public void addInfo(Object messageId){
		addInfo(messageId, Boolean.TRUE);
	}
	
	/**/
	
	@Override
	public void add(SeverityType severityType, Object object, Boolean isId) {
		Severity facesMessageSeverity = null;
		switch(severityType){
		case INFO:
			facesMessageSeverity = FacesMessage.SEVERITY_INFO;
			break;
		case WARNING:
			facesMessageSeverity = FacesMessage.SEVERITY_WARN;
			break;
		case ERROR:
			facesMessageSeverity = FacesMessage.SEVERITY_ERROR;
			break;
		}
		add(facesMessageSeverity, object.toString(), isId);
	}

}
