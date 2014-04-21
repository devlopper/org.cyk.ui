package org.cyk.ui.web.api;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.extern.java.Log;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.ui.api.UIMessageManager;
import org.cyk.utility.common.cdi.AbstractBean;

@Singleton @Log
public class WebUIMessageManager extends AbstractBean implements UIMessageManager,Serializable {
	
	private static final long serialVersionUID = -2096649010369789825L;
	
	@Inject private LanguageBusiness languageBusiness;
	
	protected FacesMessage facesMessage;
	
	/*
	public void add(Severity severity,Object text,Boolean isMessageId){
		String message = isMessageId?languageService.findText(text.toString()):text.toString();
		message = format(message);
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
		
	public void addInfo(Object text,Boolean isMessageId){
		add(FacesMessage.SEVERITY_INFO, text,isMessageId);
	}
	
	public void addInfo(Object messageId){
		addInfo(messageId, Boolean.TRUE);
	}*/
	
	
	@Override
	public UIMessageManager message(SeverityType severityType, Text summary, Text details) {
		facesMessage = new FacesMessage(severity(severityType), toString(summary), toString(details));
		return this;
	}
	
	@Override
	public UIMessageManager message(SeverityType severityType, Object object, Boolean isId) {
		facesMessage = new FacesMessage(severity(severityType), toString(new Text(object, isId)), toString(new Text(object, isId)));
		return this;
	}
		
	@Override
	public UIMessageManager throwable(Throwable throwable) {
		StringBuilder m = new StringBuilder(throwable.toString());
		if(throwable.getCause()!=null)
			m.append("\r\nCause : "+throwable.getCause());
		message(SeverityType.ERROR,new Text(m, Boolean.FALSE),new Text(m, Boolean.FALSE));
		return this;
	}

	@Override
	public void showInline() {
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}

	@Override
	public void showDialog() {
		log.warning("I dont know how to show message in dialog!!!");
	}
	
	/**/
	
	protected Severity severity(SeverityType severityType) {
		switch(severityType){
		case INFO:return FacesMessage.SEVERITY_INFO;
		case WARNING:return FacesMessage.SEVERITY_WARN;
		case ERROR:return FacesMessage.SEVERITY_ERROR;
		}
		return null;
	}
	
	protected String format(String message){
		message = StringUtils.replace(message, "\r\n", "<br/>");
		message = StringUtils.replace(message, "\n", "<br/>");
		//message = StringEscapeUtils.escapeHtml4(message);
		return message;
	}
	
	protected String toString(Text text){
		String message = Boolean.TRUE.equals(text.getIsId())?languageBusiness.findText(text.getText().toString()):text.getText().toString();
		message = format(message);
		return message;
	}





}
