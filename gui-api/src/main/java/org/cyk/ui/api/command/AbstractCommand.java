package org.cyk.ui.api.command;

import java.io.Serializable;
import java.util.logging.Level;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.AbstractBusinessException;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.UIMessageManager;
import org.cyk.ui.api.UIMessageManager.SeverityType;
import org.cyk.utility.common.AbstractMethod;
import org.cyk.utility.common.CommonUtils;

@Log
public abstract class AbstractCommand implements UICommand , Serializable {

	private static final long serialVersionUID = 3245517653342272298L;

	@Setter protected UIMessageManager messageManager;
	
	@Getter @Setter protected AbstractValidateMethod<Object> validateMethod;
	@Getter @Setter protected AbstractMethod<Object, Object> executeMethod,afterFailureMethod;
	@Getter @Setter protected AbstractSucessNotificationMessageMethod<Object> successNotificationMessageMethod;
	
	@Getter @Setter protected Boolean notifyOnSucceed=Boolean.FALSE;
	
	@Override
	public Boolean validate() {
		if(validateMethod==null)
			return true;
		return validateMethod.execute();
	}
		
	public Object execute(Object object){
		if(validate()){
			try {
				if(executeMethod==null)
					throw new RuntimeException("No execution method has been provided.");
				executeMethod.execute(object);
				return onExecuteSucceed();
			} catch (Exception exception) {
				return failure(exception);
			}
		}else
			return failure(null);
	}
	
	@Override
	public Object onExecuteSucceed() {
		if(Boolean.TRUE.equals(notifyOnSucceed)){
			String message = successNotificationMessage();
			if(StringUtils.isNotEmpty(message))
				getMessageManager().message(SeverityType.INFO, message,Boolean.FALSE).showInline();
		}
		return null;
	}
	
	@Override
	public String successNotificationMessage() {
		if(successNotificationMessageMethod==null)
			return UIManager.getInstance().text("command.execution.succeed");
		return successNotificationMessageMethod.execute();
	}
	
	@Override
	public Object onExecuteFailed(Throwable throwable) {
		return failure(throwable);		
	}
	
	@Override
	public Object failure(Throwable throwable) {
		Throwable cause = CommonUtils.getInstance().getThrowableInstanceOf(throwable, AbstractBusinessException.class);
		String message;
		if(cause==null){
			log.log(Level.SEVERE,throwable.getMessage(),throwable);
			message = UIManager.getInstance().text("command.execution.failure");
		}else{
			message = cause.getMessage();
		}
		getMessageManager().message(SeverityType.ERROR, message,Boolean.FALSE).showInline();
		if(afterFailureMethod!=null)
			afterFailureMethod.execute(throwable);
		return null;
	}
	
	@Override
	public UIMessageManager getMessageManager() {
		if(messageManager==null)
			throw new RuntimeException("No message manager has been provided.");
		return messageManager;
	}
	 
}
