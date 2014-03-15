package org.cyk.ui.api.command;

import org.cyk.ui.api.UIMessageManager;
import org.cyk.utility.common.AbstractMethod;

/**
 * A call to the server side function to be executed
 * @author Komenan Y .Christian
 *
 */
public interface UIActionCommand extends UICommand {
	
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
	
	/**/
	
	public static abstract class AbstractValidateMethod<OBJECT> extends AbstractMethod<Boolean, OBJECT>{

		private static final long serialVersionUID = 87318682837035755L;

	}
	
	public static abstract class AbstractSucessNotificationMessageMethod<OBJECT> extends AbstractMethod<String, OBJECT>{

		private static final long serialVersionUID = 87318682837035755L;

	}

}
