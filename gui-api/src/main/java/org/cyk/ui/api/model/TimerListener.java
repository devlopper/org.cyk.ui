package org.cyk.ui.api.model;

public interface TimerListener {

	void started(AbstractTimer timer,Long timestamp);
	
	void timeout(AbstractTimer timer,Long timestamp);
	
	void suspended(AbstractTimer timer,Long timestamp);
	
	void stopped(AbstractTimer timer,Long timestamp);
	
	void restarted(AbstractTimer timer,Long timestamp);
	
}
