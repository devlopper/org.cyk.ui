package org.cyk.ui.web.primefaces;

import org.cyk.ui.api.command.UICommandable;
import org.primefaces.model.menu.DefaultMenuItem;

public class Utils {

	public static void configure(DefaultMenuItem aMenuItem,UICommandable aCommand){
		aMenuItem.setValue(aCommand.getLabel());
		aMenuItem.setUpdate("@form");
		
		if(UICommandable.ProcessGroup.THIS.equals(aCommand.getProcessGroup()))
			aMenuItem.setProcess("@this");
	}
	
}
