package org.cyk.ui.api.command;

public interface CommandListener {

	void transfer(UICommand command,Object parameter) throws Exception;
	
	Boolean validate(UICommand command,Object parameter);
	
	void serve(UICommand command,Object parameter);
	
	Object succeed(UICommand command,Object parameter);
	
	Object fail(UICommand command,Object parameter,Throwable throwable);
	
}
