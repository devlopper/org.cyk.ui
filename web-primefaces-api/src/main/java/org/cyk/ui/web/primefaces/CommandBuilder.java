package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import org.cyk.ui.api.command.UICommand;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.model.menu.DefaultMenuItem;

public class CommandBuilder implements Serializable {

	private static final long serialVersionUID = 6706193290921067166L;

	private static final String EL_MENU_ITEM_COMMAND_FORMAT = "#"+"{"+ "%s.%s.menu.command('%s').execute(null)}";
	
	public Object build(UICommand aCommand){
		CommandButton command = new CommandButton();
		
		return command;
	}
	
	public DefaultMenuItem menuItem(UICommand aCommand,String managedBeanName, String formName){
		DefaultMenuItem	menuItem = new DefaultMenuItem();
		menuItem.setValue(aCommand.getLabel());
		menuItem.setUpdate("@form");
		menuItem.setCommand(String.format(EL_MENU_ITEM_COMMAND_FORMAT, managedBeanName,formName,aCommand.getIdentifier())); 
		if(UICommand.ProcessGroup.THIS.equals(aCommand.getProcessGroup()))
			menuItem.setProcess("@this");		
		
		return menuItem;
	}
	
}
