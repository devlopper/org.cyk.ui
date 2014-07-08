package org.cyk.ui.desktop.api;

import java.io.Serializable;

import javax.inject.Singleton;

import lombok.extern.java.Log;

import org.cyk.ui.api.AbstractMessageManager;
import org.cyk.ui.api.UIMessageManager;
import org.cyk.ui.api.UIMessageManager.SeverityType;

@Singleton @Log
public class DesktopUIMessageManager extends AbstractMessageManager<DesktopUIMessage, SeverityType> implements UIMessageManager,Serializable {
	
	private static final long serialVersionUID = -2096649010369789825L;
	
	protected DesktopUIMessage buildMessage(SeverityType severity, String summary, String details) {
		return new DesktopUIMessage(severity,summary, details);
	}
	
	@Override
	protected SeverityType severityInfo() {
		return SeverityType.INFO;
	}
	
	@Override
	protected SeverityType severityWarning() {
		return SeverityType.WARNING;
	}
	
	@Override
	protected SeverityType severityError() {
		return SeverityType.ERROR;
	}
	
	@Override
	public void showInline() {
		log.warning("I dont know how to show message in inline!!!");
	}

	@Override
	public void showDialog() {
		log.warning("I dont know how to show message in dialog!!!");
	}
	
	/**/
	
	protected SeverityType severity(SeverityType severityType) {
		return severityType;
	}

}
