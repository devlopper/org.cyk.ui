package org.cyk.ui.api.command.menu;

import java.util.Collection;

import org.cyk.ui.api.Icon;
import org.cyk.ui.api.command.UICommandable;

public interface UIMenu {
	
	enum RenderType{PLAIN,SLIDE,PANEL,TAB,BREAD_CRUMB}
	
	Collection<UICommandable> getCommandables();

	UICommandable getCommandable(String anIdentifier);
	UICommandable remove(String anIdentifier);
	
	UICommandable addCommandable(String labelId,Icon icon);
	UICommandable addCommandable(UICommandable commandable);
	UICommandable addCommandable(String labelId,Icon icon,Object view);
	
	RenderType getRenderType();
	void setRenderType(RenderType aRenderType);
	
	UICommandable getRequestedCommandable();
	void setRequestedCommandable(UICommandable commandable);
	void setRequestedCommandable(String identifier);
	
}
