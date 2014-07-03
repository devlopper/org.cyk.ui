package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.command.UICommandable;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.event.SelectEvent;

@Getter @Setter
public class Command implements Serializable {

	private static final long serialVersionUID = 6706193290921067166L;

	private UICommandable commandable;
	private CommandButton commandButton;
	private String update;
	
	public Command(UICommandable aCommandable,String update) {
		super();
		this.commandable = aCommandable;
		this.update = update;
	}
	
	public Command(UICommandable aCommandable) {
		this(aCommandable,":form:contentPanel :form:menuPanel");
	}
	
	public CommandButton getCommandButton(){
		if(commandButton==null)
			commandButton = CommandBuilder.getInstance().commandButton(this);
			
		return commandButton;
	}
	
	public void onDialogReturn(SelectEvent selectEvent){}
	
}
