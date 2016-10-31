package org.cyk.ui.api.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface CommandListener {

	@AllArgsConstructor @Getter
	public enum AfterServeState{
		SUCCEED(Boolean.TRUE,"command.serve.succeed"),
		FAIL(Boolean.TRUE,"command.serve.fail"),
		
		;
		
		private Boolean notify;
		private String notificationMessageId;
	};
	
	void transfer(UICommand command,Object parameter) throws Exception;
	
	Boolean validate(UICommand command,Object parameter);
	
	void serve(UICommand command,Object parameter);
	
	Object succeed(UICommand command,Object parameter);
	
	Object fail(UICommand command,Object parameter,Throwable throwable);
	
	/* */
	
	Boolean notifyAfterServe(UICommand command,AfterServeState state);
	
	String notificationMessageIdAfterServe(UICommand command,Object parameter,AfterServeState state);
}
