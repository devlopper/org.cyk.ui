package org.cyk.ui.api.command;

import java.io.Serializable;

public class CommandAdapter implements CommandListener,Serializable {

	private static final long serialVersionUID = 4033611078759559395L;

	@Override
	public void transfer(UICommand command, Object parameter) throws Exception {
		
	}

	@Override
	public Boolean validate(UICommand command, Object parameter) {
		return null;
	}

	@Override
	public void serve(UICommand command, Object parameter) {
		
	}

	@Override
	public Object succeed(UICommand command, Object parameter) {
		return null;
	}

	@Override
	public Object fail(UICommand command, Object parameter, Throwable throwable) {
		return null;
	}

	@Override
	public Boolean notifyAfterServe(UICommand command, AfterServeState state) {
		return null;
	}

	@Override
	public String notificationMessageIdAfterServe(UICommand command, Object parameter, AfterServeState state) {
		return null;
	}

}
