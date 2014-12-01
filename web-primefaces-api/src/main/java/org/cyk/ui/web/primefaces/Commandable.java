package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.ui.web.api.command.AbstractWebCommandable;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.menu.MenuModel;

@NoArgsConstructor
public class Commandable extends AbstractWebCommandable implements Serializable {

	private static final long serialVersionUID = 6706193290921067166L;

	private CommandButton commandButton;
	@Getter @Setter private MenuModel menu;
	@Getter @Setter private String update;
	
	{
		update = ":form:contentPanel :form:menuPanel";
	}
	
	public CommandButton getButton(){
		if(commandButton==null)
			commandButton = CommandBuilder.getInstance().commandButton(this);
			
		return commandButton;
	}
	
	public void onDialogReturn(SelectEvent selectEvent){}
	
}
