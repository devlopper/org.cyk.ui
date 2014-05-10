package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.command.UICommandable;
import org.primefaces.model.menu.DefaultMenuItem;

@Getter @Setter
public class PrimefacesMenuItem extends DefaultMenuItem implements Serializable {

	private static final long serialVersionUID = 6706193290921067166L;

	private UICommandable commandable;

	public PrimefacesMenuItem(UICommandable aCommandable) {
		super();
		this.commandable = aCommandable;
		setValue(aCommandable.getLabel());
		setUpdate("@form");
		if(UICommandable.ProcessGroup.THIS.equals(aCommandable.getProcessGroup()))
			setProcess("@this");	
	}
	
	
	
}
