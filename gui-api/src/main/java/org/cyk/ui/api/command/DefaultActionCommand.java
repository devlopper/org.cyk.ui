package org.cyk.ui.api.command;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.ui.api.UIMessageManager;
import org.cyk.ui.api.UIMessageManager.SeverityType;
import org.cyk.utility.common.AbstractMethod;

public class DefaultActionCommand implements UIActionCommand , Serializable {

	private static final long serialVersionUID = 3245517653342272298L;

	@Setter protected UIMessageManager messageManager;
	
	@Getter @Setter protected AbstractValidateMethod<Object> validateMethod;
	@Getter @Setter protected AbstractMethod<Object, Object> executeMethod;
	@Getter @Setter protected AbstractSucessNotificationMessageMethod<Object> successNotificationMessageMethod;
	
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
		String message = successNotificationMessage();
		if(StringUtils.isNotEmpty(message))
			getMessageManager().message(SeverityType.INFO, message,Boolean.FALSE).showInline();
		return null;
	}
	
	@Override
	public String successNotificationMessage() {
		if(successNotificationMessageMethod==null)
			return "Success ";
		return successNotificationMessageMethod.execute();
	}
	
	@Override
	public Object onExecuteFailed(Throwable throwable) {
		return failure(throwable);		
	}
	
	@Override
	public Object failure(Throwable throwable) {
		getMessageManager().message(SeverityType.ERROR, throwable,Boolean.FALSE).showInline();
		return null;
	}
	
	@Override
	public UIMessageManager getMessageManager() {
		if(messageManager==null)
			throw new RuntimeException("No message manager has been provided.");
		return messageManager;
	}
	
	 
}
