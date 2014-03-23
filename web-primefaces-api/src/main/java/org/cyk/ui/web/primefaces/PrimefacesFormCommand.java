package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.form.UIFormCommand;
import org.primefaces.component.commandbutton.CommandButton;

@Getter @Setter
public class PrimefacesFormCommand extends CommandButton implements Serializable {

	private static final long serialVersionUID = 6706193290921067166L;

	private UIFormCommand command;

	public PrimefacesFormCommand(UIFormCommand aCommand) {
		super();
		this.command = aCommand;
		setValue(aCommand.getLabel());
		setUpdate("@form");
		if(UIFormCommand.ProcessGroup.THIS.equals(aCommand.getProcessGroup()))
			setProcess("@this");
		
	}
	
	
	
}
