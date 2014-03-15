package org.cyk.ui.api;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface UIMessageManager {
	
	public enum SeverityType{ERROR,WARNING,INFO}
	
	UIMessageManager message(SeverityType severityType,Text summary,Text details);
	
	UIMessageManager message(SeverityType severityType,Object object,Boolean isId);
	
	UIMessageManager throwable(Throwable throwable);
	
	//UIMessageManager severity(SeverityType severityType);
	
	void showInline();
	
	void showDialog();
	
	
	//void add(SeverityType severityType,Object object,Boolean isId);
	
	//void addError(Throwable throwable);
	
	//void showInDialog(SeverityType severityType,Object title,Object object,Boolean isId);

	/**/
	
	@Getter @AllArgsConstructor
	public static class Text implements Serializable{

		private static final long serialVersionUID = 3095126646590079656L;
		
		private Object text;
		private Boolean isId;
		
	}
	
}
