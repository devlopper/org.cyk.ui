package org.cyk.ui.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.UIProvider;
import org.cyk.ui.api.command.CommandListener;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;

@Getter @Setter
public class AbstractTimer implements CommandListener, Serializable {

	private static final long serialVersionUID = 2876480260626169563L;

	protected Collection<TimerListener> timerListeners = new ArrayList<>();
	
	protected Boolean running = Boolean.FALSE, autoStart = Boolean.FALSE,visible=Boolean.FALSE,runOnce=Boolean.TRUE,stopSilently=Boolean.FALSE;
	protected Integer interval = 3,timeoutCount = 0;
	
	protected UICommandable startCommandable,restartCommandable,suspendCommandable,stopCommandable;
	
	public AbstractTimer() {
		startCommandable = UIProvider.getInstance().createCommandable(this,"command.start", null,null,null);
		stopCommandable = UIProvider.getInstance().createCommandable(this,"command.stop", null,null,null);
		suspendCommandable = UIProvider.getInstance().createCommandable(this,"command.suspend", null,null,null);
		restartCommandable = UIProvider.getInstance().createCommandable(this,"command.restart", null,null,null);
		
		restartCommandable.setRendered(Boolean.FALSE);
		suspendCommandable.setRendered(Boolean.FALSE);
	}
	
	public void start(){
		running = Boolean.TRUE;
		timeoutCount = 0;
		for(TimerListener listener : timerListeners)
			listener.started(this, System.currentTimeMillis());
	}
	
	public void stop(){
		running = Boolean.FALSE;
		for(TimerListener listener : timerListeners)
			listener.stopped(this, System.currentTimeMillis());
	}
	
	@Override
	public void transfer(UICommand command, Object parameter) throws Exception {}

	@Override
	public Boolean validate(UICommand command, Object parameter) {
		return null;
	}

	@Override
	public void serve(UICommand command, Object parameter) {
		if(command == startCommandable.getCommand()){
			start();
		}else if(command == stopCommandable.getCommand()){
			stop();
		}
	}
	
	public void onTimeout(){
		timeoutCount++;
		for(TimerListener listener : timerListeners)
			listener.timeout(this, System.currentTimeMillis());
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
	public String notificationMessageIdAfterServe(UICommand command,Object parameter, AfterServeState state) {
		return null;
	}
	
}
