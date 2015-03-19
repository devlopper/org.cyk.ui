package org.cyk.ui.api;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.event.Notification;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface UIMessageManager {
	
	public enum SeverityType{ERROR,WARNING,INFO}
	
	UIMessageManager message(SeverityType severityType,Text summary,Text details);
	
	UIMessageManager message(SeverityType severityType,Object object,Boolean isId);
	
	UIMessageManager notification(Notification notification);
	
	UIMessageManager notifications(Collection<Notification> notifications);
	
	UIMessageManager throwable(Throwable throwable);
	
	void showInline();
	
	void showDialog();
	
	void showGrowl();
	
	/**/
	
	@Getter @AllArgsConstructor
	public static class Text implements Serializable{

		private static final long serialVersionUID = 3095126646590079656L;
		
		private Object text;
		private Boolean isId;
		
		public Text(Object text) {
			this(text,Boolean.TRUE); 
		}
		
		
	}
	
}
