package org.cyk.ui.api.model;

import java.io.Serializable;


public class TimerAdapter implements TimerListener,Serializable {

	static final long serialVersionUID = -5777529943356788848L;

	@Override
	public void started(AbstractTimer timer, Long timestamp) {
		
	}

	@Override
	public void timeout(AbstractTimer timer, Long timestamp) {
		
	}

	@Override
	public void suspended(AbstractTimer timer, Long timestamp) {
		
	}

	@Override
	public void stopped(AbstractTimer timer, Long timestamp) {
		
	}

	@Override
	public void restarted(AbstractTimer timer, Long timestamp) {
		
	}

}
