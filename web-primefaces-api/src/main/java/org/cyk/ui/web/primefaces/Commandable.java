package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.web.api.command.AbstractWebCommandable;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.event.SelectEvent;

@NoArgsConstructor
public class Commandable extends AbstractWebCommandable implements Serializable {

	private static final long serialVersionUID = 6706193290921067166L;

	private CommandButton commandButton;
	@Getter @Setter private String update;
	
	{
		update = ":form:contentPanel :form:menuPanel";
	}
	
	//TODO Both constructors has to be removed. Customization to be done in listener
	public Commandable(UICommandable aCommandable,String update) {
		super();
		//this.commandable = aCommandable;
		this.update = update;
	}
	
	public Commandable(UICommandable aCommandable) {
		this(aCommandable,":form:contentPanel :form:menuPanel");
	}
	
	public CommandButton getButton(){
		if(commandButton==null)
			commandButton = CommandBuilder.getInstance().commandButton(this);
			
		return commandButton;
	}
	
	public void onDialogReturn(SelectEvent selectEvent){}
	
}
