package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.model.AbstractTimer;
import org.omnifaces.util.Ajax;

@Getter @Setter
public class Timer extends AbstractTimer implements Serializable {

	private static final long serialVersionUID = 1448442585929575156L;

	private String onTimerComplete;
	
	@Override
	public void serve(UICommand command, Object parameter) {
		super.serve(command, parameter);
		if(command == startCommandable.getCommand()){
			Ajax.oncomplete("PF('timer').start();");
		}else if(command == stopCommandable.getCommand()){
			Ajax.oncomplete("PF('timer').stop(true);");
		}
	}
	
}
