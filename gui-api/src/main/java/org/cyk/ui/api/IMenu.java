package org.cyk.ui.api;

import java.util.Collection;

import org.cyk.ui.api.command.ICommand;

public interface IMenu {
	
	Collection<ICommand> getCommands();

}
