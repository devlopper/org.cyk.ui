package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.command.UICommand;
import org.primefaces.model.menu.DefaultMenuItem;

@Getter @Setter
public class PrimefacesMenuItem extends DefaultMenuItem implements Serializable {

	private static final long serialVersionUID = 6706193290921067166L;

	private UICommand _command;

	public PrimefacesMenuItem(UICommand aCommand) {
		super();
		this._command = aCommand;
		setValue(aCommand.getLabel());
		setUpdate("@form");
		if(UICommand.ProcessGroup.THIS.equals(aCommand.getProcessGroup()))
			setProcess("@this");	
	}
	
	
	
}
