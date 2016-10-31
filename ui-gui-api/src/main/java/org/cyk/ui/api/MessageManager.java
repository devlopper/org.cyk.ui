package org.cyk.ui.api;

import java.util.Collection;

import org.cyk.system.root.model.event.Notification;

public class MessageManager implements UIMessageManager {
	
	public static UIMessageManager INSTANCE;

	@Override
	public UIMessageManager message(SeverityType severityType, Text summary, Text details) {
		return null;
	}

	@Override
	public UIMessageManager message(SeverityType severityType, Object object, Boolean isId) {
		return null;
	}

	@Override
	public UIMessageManager throwable(Throwable throwable) {
		return null;
	}

	@Override
	public void showInline() {
		
	}

	@Override
	public void showDialog() {
		
	}

	@Override
	public UIMessageManager notification(Notification notification) {
		return null;
	}

	@Override
	public UIMessageManager notifications(Collection<Notification> notifications) {
		return null;
	}

	@Override
	public void showGrowl() {
		
	}

	@Override
	public void clear() {
		
	}

}
