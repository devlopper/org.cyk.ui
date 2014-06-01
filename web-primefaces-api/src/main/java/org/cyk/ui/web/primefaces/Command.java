package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.command.UICommandable;
import org.primefaces.component.commandbutton.CommandButton;

@Getter @Setter
public class Command implements Serializable {

	private static final long serialVersionUID = 6706193290921067166L;

	private UICommandable commandable;
	private CommandButton commandButton;
	
	public Command(UICommandable aCommandable) {
		super();
		this.commandable = aCommandable;
	}
	
	public CommandButton getCommandButton(){
		if(commandButton==null)
			commandButton = CommandBuilder.getInstance().commandButton(commandable);
			
		return commandButton;
	}
	
}
