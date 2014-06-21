package org.cyk.ui.api;

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

}
