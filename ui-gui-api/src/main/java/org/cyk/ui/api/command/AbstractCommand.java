package org.cyk.ui.api.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.AbstractBusinessThrowable;
import org.cyk.system.root.business.impl.validation.ExceptionUtils;
import org.cyk.ui.api.UIMessageManager;
import org.cyk.ui.api.UIMessageManager.SeverityType;
import org.cyk.ui.api.UIMessageManager.Text;
import org.cyk.ui.api.command.CommandListener.AfterServeState;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.computation.ExecutionProgress;
import org.cyk.utility.common.helper.ThrowableHelper;

import lombok.Getter;
import lombok.Setter;

//@Log
public abstract class AbstractCommand extends AbstractBean implements UICommand , Serializable {

	private static final long serialVersionUID = 3245517653342272298L;

	@Setter protected UIMessageManager messageManager;
	@Getter @Setter protected Boolean confirm=Boolean.FALSE,showExecutionProgress=Boolean.FALSE;
	@Getter @Setter protected ExecutionProgress executionProgress;
	@Getter protected Collection<CommandListener> commandListeners = new ArrayList<>();
	
	public Object execute(Object object){
		try {
			transfer(object);
		}catch (Exception exception) {
			return fail(object,exception);
		}
		if(validate(object)){
			try {
				serve_(object);
				return success(object);
			} catch (Exception exception) {
				return fail(object,exception);
			}
		}else
			return fail(object,null);
	}
	
	@Override
	public Object execute() {
		return execute(null);
	}
	
	private void transfer(Object object) throws Exception {
		for(CommandListener listener : commandListeners)
			listener.transfer(this, object);
	}
	
	private Boolean validate(Object object) {
		Boolean valid = Boolean.TRUE;
		for(int i=0;i<commandListeners.size() && Boolean.TRUE.equals(valid);i++){
			Boolean v = ((List<CommandListener>)commandListeners).get(i).validate(this, object);
			if(v!=null)
				valid = v;
		}
		return Boolean.TRUE.equals(valid);
	}
	
	private void serve_(Object object){
		for(CommandListener listener : commandListeners)
			listener.serve(this, object);
	}
		
	public Object success(Object object) {
		AfterServeState state = AfterServeState.SUCCEED;
		Boolean notify = Boolean.TRUE;
		for(int i=0;i<commandListeners.size() && Boolean.TRUE.equals(notify);i++){
			Boolean v = ((List<CommandListener>)commandListeners).get(i).notifyAfterServe(this, state);
			if(v!=null)
				notify = v;
		}
		if(Boolean.TRUE.equals(notify)){
			//getMessageManager().message(SeverityType.INFO, UIManager.getInstance().text("command.execution.success"),Boolean.FALSE).showInline();
			for(int i=0;i<commandListeners.size();i++){
				String messageId = ((List<CommandListener>)commandListeners).get(i).notificationMessageIdAfterServe(this, object, state);
				if(StringUtils.isNotEmpty(messageId)){
					getMessageManager().message(SeverityType.INFO, new Text(messageId+".summary"),new Text(messageId+".details")).showInline();
					//System.out.println("success() A : "+FacesContext.getCurrentInstance().getMessageList());
				}
			}
		}
		for(CommandListener listener : commandListeners)
			listener.succeed(this, object);
		return null;
	}
	
	private Object fail(Object parameter,Throwable throwable) {
		Throwable cause = inject(ThrowableHelper.class).getInstanceOf(throwable, AbstractBusinessThrowable.class);
		Set<String> messages = new LinkedHashSet<>();
		if(throwable!=null){
			//logThrowable(throwable);
			//throwable.printStackTrace();
		}
		if(cause==null){
			if(throwable!=null){
				//logThrowable(throwable);
				messages.add(ExceptionUtils.getInstance().getMessage(throwable));
			}
			//messages.add(UIManager.getInstance().text("command.serve.failure.summary"));
		}else{
			if(cause instanceof AbstractBusinessThrowable)
				messages.addAll(((AbstractBusinessThrowable)cause).getFields().getMessages());
		}
		
		AfterServeState state = AfterServeState.FAIL;
		Boolean notify = Boolean.TRUE;
		for(int i=0;i<commandListeners.size() && Boolean.TRUE.equals(notify);i++){
			Boolean v = ((List<CommandListener>)commandListeners).get(i).notifyAfterServe(this, state);
			if(v!=null)
				notify = v;
		}
		
		if(Boolean.TRUE.equals(notify)){
			for(String message : messages)
				getMessageManager().message(SeverityType.ERROR, message,Boolean.FALSE).showInline();
			for(int i=0;i<commandListeners.size();i++){
				//String message = ((List<CommandListener>)commandListeners).get(i).notificationMessageIdAfterServe(this, parameter, state);
				//if(StringUtils.isNotEmpty(message))
				//	getMessageManager().message(SeverityType.ERROR, message,Boolean.FALSE).showInline();
			}
		}
		
		for(CommandListener listener : commandListeners)
			listener.fail(this, parameter,throwable);
		
		return null;
	}
	
	@Override
	public UIMessageManager getMessageManager() {
		//if(messageManager==null)
		//	throw new RuntimeException("No message manager has been provided.");
		return messageManager;
	}
	 
}
