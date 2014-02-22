package org.cyk.ui.api;

public interface IMessageManager {
	
	public enum SeverityType{ERROR,WARNING,INFO}
	
	void add(SeverityType severityType,Object object,Boolean isId);

}
