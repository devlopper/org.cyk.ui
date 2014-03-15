package org.cyk.ui.api;

public interface UIMessageManager {
	
	public enum SeverityType{ERROR,WARNING,INFO}
	
	void add(SeverityType severityType,Object object,Boolean isId);
	
	void addError(Throwable throwable);
	
	void showInDialog(SeverityType severityType,Object title,Object object,Boolean isId);

}
