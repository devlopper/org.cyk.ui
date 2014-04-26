package org.cyk.ui.web.primefaces;

import org.cyk.ui.api.command.UICommand;
import org.primefaces.model.menu.DefaultMenuItem;

public class Utils {

	public static void configure(DefaultMenuItem aMenuItem,UICommand aCommand){
		aMenuItem.setValue(aCommand.getLabel());
		aMenuItem.setUpdate("@form");
		
		if(UICommand.ProcessGroup.THIS.equals(aCommand.getProcessGroup()))
			aMenuItem.setProcess("@this");
	}
	
}
