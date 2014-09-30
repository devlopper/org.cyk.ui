package org.cyk.ui.api.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.AbstractBusinessException;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.UIMessageManager;
import org.cyk.ui.api.UIMessageManager.SeverityType;
import org.cyk.utility.common.CommonUtils;

@Log
public abstract class AbstractCommand implements UICommand , Serializable {

	private static final long serialVersionUID = 3245517653342272298L;

	@Setter protected UIMessageManager messageManager;
	
	//@Getter @Setter protected AbstractValidateMethod<Object> validateMethod;
	@Getter @Setter protected AbstractNotifyOnSucceedMethod<Object> notifyOnSucceedMethod;
	//@Getter @Setter protected AbstractMethod<Object, Object> executeMethod,afterFailureMethod/*,afterSuccessNotificationMessageMethod*/;
	@Getter @Setter protected AbstractSucessNotificationMessageMethod<Object> successNotificationMessageMethod;
	
	@Getter protected Collection<CommandListener> listeners = new ArrayList<>();
	
	public Object execute(Object object){
		try {
			transfer(object);
		}catch (Exception exception) {
			return fail(object,exception);
		}
		if(validate(object)){
			try {
				execute_(object);
				return success(object);
			} catch (Exception exception) {
				return fail(object,exception);
			}
		}else
			return fail(object,null);
	}
	
	private void transfer(Object object) throws Exception {
		for(CommandListener listener : listeners)
			listener.transfer(this, object);
	}
	
	private Boolean validate(Object object) {
		Boolean valid = Boolean.TRUE;
		for(int i=0;i<listeners.size() && Boolean.TRUE.equals(valid);i++)
			valid = ((List<CommandListener>)listeners).get(i).validate(this, object);
		return Boolean.TRUE.equals(valid);
	}
	
	private void execute_(Object object){
		for(CommandListener listener : listeners)
			listener.serve(this, object);
	}
		
	public Object success(Object object) {
		Boolean notifyOnSucceed = notifyOnSucceedMethod!=null && notifyOnSucceedMethod.execute(object);
		if(Boolean.TRUE.equals(notifyOnSucceed)){
			String message = successNotificationMessage();
			if(StringUtils.isNotEmpty(message))
				getMessageManager().message(SeverityType.INFO, message,Boolean.FALSE).showInline();
		}
		for(CommandListener listener : listeners)
			listener.succeed(this, object);
		//if(afterSuccessNotificationMessageMethod!=null)
		//	afterSuccessNotificationMessageMethod.execute(object);
		return null;
	}
	
	@Override
	public String successNotificationMessage() {
		if(successNotificationMessageMethod==null)
			return UIManager.getInstance().text("command.execution.succeed");
		return successNotificationMessageMethod.execute();
	}
	
	private Object fail(Object parameter,Throwable throwable) {
		Throwable cause = CommonUtils.getInstance().getThrowableInstanceOf(throwable, AbstractBusinessException.class);
		Set<String> messages = new LinkedHashSet<>();
		if(cause==null){
			if(throwable!=null)
				log.log(Level.SEVERE, throwable.getMessage(),throwable);
			messages.add(UIManager.getInstance().text("command.execution.failure"));
		}else{
			if(cause instanceof AbstractBusinessException)
				messages.addAll(((AbstractBusinessException)cause).getMessages());
		}
		
		for(String message : messages)
			getMessageManager().message(SeverityType.ERROR, message,Boolean.FALSE).showInline();
		
		for(CommandListener listener : listeners)
			listener.fail(this, parameter,throwable);
		
		return null;
	}
	
	@Override
	public UIMessageManager getMessageManager() {
		if(messageManager==null)
			throw new RuntimeException("No message manager has been provided.");
		return messageManager;
	}
	 
}
