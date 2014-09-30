package org.cyk.ui.api.command;

import java.util.Collection;

import org.cyk.ui.api.UIMessageManager;
import org.cyk.utility.common.AbstractMethod;

/**
 * An element on which user can interact to request something
 * @author Komenan Y .Christian
 *
 */
public interface UICommand {
	
	Collection<CommandListener> getListeners();
	
	/**
	 * Validate data provided by user
	 * @return true if success else false
	 */
	//Boolean validate(Object object);
	
	/**
	 * The code to be executed when validation has succeed
	 */
	Object execute(Object object);
		
	/**
	 * The code to be executed on <code>execute()</code> success
	 */
	//Object onExecuteSucceed(Object object);
	
	/**
	 * The code to be executed on <code>execute()</code> failure
	 * @param throwable
	 */
	//Object onExecuteFailed(Throwable throwable);
	
	AbstractNotifyOnSucceedMethod<Object> getNotifyOnSucceedMethod();
	
	void setNotifyOnSucceedMethod(AbstractNotifyOnSucceedMethod<Object> aMethod);
	
	String successNotificationMessage();
	
	/**
	 * The code to be executed on error
	 * @param throwable
	 */
	//Object failure(Throwable throwable);
	
	UIMessageManager getMessageManager();
	
	void setMessageManager(UIMessageManager messageManager);
	
	//void setValidateMethod(AbstractValidateMethod<Object> method);
	
	//void setExecuteMethod(AbstractMethod<Object, Object> method);
	
	//void setAfterFailureMethod(AbstractMethod<Object, Object> method);
	
	//void setAfterSuccessNotificationMessageMethod(AbstractMethod<Object, Object> method);
	
	
	/**/
	public static abstract class AbstractValidateMethod<OBJECT> extends AbstractMethod<Boolean, OBJECT>{

		private static final long serialVersionUID = 87318682837035755L;

	}
	
	public static abstract class AbstractNotifyOnSucceedMethod<OBJECT> extends AbstractMethod<Boolean, OBJECT>{

		private static final long serialVersionUID = 87318682837035755L;

	}
	
	public static abstract class AbstractSucessNotificationMessageMethod<OBJECT> extends AbstractMethod<String, OBJECT>{

		private static final long serialVersionUID = 87318682837035755L;

	}
	
	
	

	
	
	
	
}
