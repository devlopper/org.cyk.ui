package org.cyk.ui.api.command;

import java.util.Collection;

public interface UIMenu {
	
	Collection<UICommand> getCommands();

	UICommand command(String anIdentifier);
}
