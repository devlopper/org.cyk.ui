package org.cyk.ui.api.model.table;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.command.DefaultCommand;
import org.cyk.ui.api.command.DefaultMenu;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UIMenu;
import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter
public class UIDimensionConfig extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 3307695889353490821L;
 
	protected UICommand newCommand;
	protected UIMenu menu = new DefaultMenu();
	
	@Override
	protected void initialisation() {
		super.initialisation();
		newCommand = new DefaultCommand();
		newCommand.setLabel("NOUVEAU");
		menu.getCommands().add(newCommand);
	}
	
}
