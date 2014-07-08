package org.cyk.ui.api;

import java.io.Serializable;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractMessageManager<MESSAGE,SEVERITY> extends AbstractBean implements UIMessageManager,Serializable {
	
	private static final long serialVersionUID = -2096649010369789825L;
	
	@Inject protected LanguageBusiness languageBusiness;
	
	protected MESSAGE builtMessage;
	
	protected abstract MESSAGE buildMessage(SEVERITY severity,String summary, String details);
	
	protected abstract SEVERITY severityInfo();
	protected abstract SEVERITY severityWarning();
	protected abstract SEVERITY severityError();
	
	@Override
	public UIMessageManager message(SeverityType severityType, Text summary, Text details) {
		builtMessage = buildMessage(severity(severityType), toString(summary), toString(details));
		return this;
	}
	
	@Override
	public UIMessageManager message(SeverityType severityType, Object object, Boolean isId) {
		builtMessage = buildMessage(severity(severityType), toString(new Text(object, isId)), toString(new Text(object, isId)));
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

	/**/
	
	protected SEVERITY severity(SeverityType severityType) {
		switch(severityType){
		case INFO:return severityInfo();
		case WARNING:return severityWarning();
		case ERROR:return severityError();
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
