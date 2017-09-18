package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.ui.web.primefaces.CommandHelper.Command;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class CommandDemoPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private Command command1 = new Command();
	
	@Override
	protected void initialisation() { 
		super.initialisation(); 
		command1 = new Command(){
			private static final long serialVersionUID = 1L;
			
		};
		command1.setProperty(Command.COMMAND_PROPERTY_NAME_LABEL, "Command 1");
		command1.setProperty(Command.COMMAND_PROPERTY_NAME_ICON, "ui-icon-trash");
	}
	
}
