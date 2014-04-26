package org.cyk.ui.api.command;

import org.cyk.ui.api.UIMessageManager;
import org.cyk.utility.common.AbstractMethod;

/**
 * An element on which user can interact to request something
 * @author Komenan Y .Christian
 *
 */
public interface UICommand {
	
	String getIdentifier();
	
	void setIdentifier(String anIdentifier);
	
	String getLabel();
	
	void setLabel(String label);
	
	String getIcon();
	
	void setIcon(String icon);

	String getTooltip();
	
	void setTooltip(String label);
	
	/**
	 * Validate data provided by user
	 * @return true if success else false
	 */
	Boolean validate();
	
	/**
	 * The code to be executed when validation has succeed
	 */
	Object execute(Object object);
		
	/**
	 * The code to be executed on <code>execute()</code> success
	 */
	Object onExecuteSucceed();
	
	/**
	 * The code to be executed on <code>execute()</code> failure
	 * @param throwable
	 */
	Object onExecuteFailed(Throwable throwable);
	
	Boolean getNotifyOnSucceed();
	
	void setNotifyOnSucceed(Boolean aValue);
	
	String successNotificationMessage();
	
	/**
	 * The code to be executed on error
	 * @param throwable
	 */
	Object failure(Throwable throwable);
	
	UIMessageManager getMessageManager();
	
	void setMessageManager(UIMessageManager messageManager);
	
	void setValidateMethod(AbstractValidateMethod<Object> method);
	
	void setExecuteMethod(AbstractMethod<Object, Object> method);
	
	void setSuccessNotificationMessageMethod(AbstractSucessNotificationMessageMethod<Object> method);
	
	EventListener getEventListener();
	
	void setEventListener(EventListener anEventListener);
	
	ProcessGroup getProcessGroup();
	
	void setProcessGroup(ProcessGroup aProcessGroup);
	
	RenderType getRenderType();
	
	void setRenderType(RenderType aRenderType);
	
	Boolean getIsNavigation();
	
	/**/
	
	public static abstract class AbstractValidateMethod<OBJECT> extends AbstractMethod<Boolean, OBJECT>{

		private static final long serialVersionUID = 87318682837035755L;

	}
	
	public static abstract class AbstractSucessNotificationMessageMethod<OBJECT> extends AbstractMethod<String, OBJECT>{

		private static final long serialVersionUID = 87318682837035755L;

	}
	
	public enum EventListener{NONE,CLICK}

	public enum ProcessGroup{THIS,FORM,PARTIAL}
	
	public enum RenderType{BUTTON,LINK}
	
}
