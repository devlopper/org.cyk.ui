package org.cyk.ui.api;

import java.util.Collection;

import org.cyk.ui.api.command.UICommand;

public interface UIMenu {
	
	Collection<UICommand> getCommands();

}
