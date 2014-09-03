package org.cyk.ui.api.command;

import java.util.Collection;

public interface UIMenu {
	
	Collection<UICommandable> getCommandables();

	UICommandable commandable(String anIdentifier);
	
	void addCommandable(UICommandable commandable);
}
