package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.command.UICommand;
import org.primefaces.component.commandbutton.CommandButton;

@Getter @Setter
public class Command implements Serializable {

	private static final long serialVersionUID = 6706193290921067166L;

	private UICommand model;
	private String managedBeanName = "dynaFormController";
	private String formName = "myForm";
	private String commandName = "primefacesSubmitCommand";
	
	private CommandButton commandButton;
	
	public Command(UICommand aModel) {
		super();
		this.model = aModel;
	}
	
	public CommandButton getCommandButton(){
		if(commandButton==null){
			commandButton = new CommandButton();
			commandButton.setValue(model.getLabel());
			commandButton.setUpdate("@form");
			if(UICommand.ProcessGroup.THIS.equals(model.getProcessGroup()))
				commandButton.setProcess("@this");		
		}
		return commandButton;
	}
	
}
