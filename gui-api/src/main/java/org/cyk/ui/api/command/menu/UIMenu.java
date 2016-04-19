package org.cyk.ui.api.command.menu;

import java.util.Collection;

import org.cyk.ui.api.Icon;
import org.cyk.ui.api.command.IconType;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.ViewType;

public interface UIMenu {
	
	enum RenderType{PLAIN,SLIDE,PANEL,TAB,BREAD_CRUMB}
	
	Collection<UICommandable> getCommandables();

	UICommandable commandable(String anIdentifier);
	UICommandable remove(String anIdentifier);
	
	UICommandable addCommandable(String labelId,Icon icon);
	UICommandable addCommandable(UICommandable commandable);
	UICommandable addCommandable(String labelId,Icon icon,ViewType viewType);
	UICommandable addCommandable(String labelId,Icon icon,Object viewId);
	
	RenderType getRenderType();
	void setRenderType(RenderType aRenderType);
	
	UICommandable getRequestedCommandable();
	void setRequestedCommandable(UICommandable commandable);
	void setRequestedCommandable(String identifier);
	
	//void setRequestedCommandable(String identifier);
	//Integer getIndex(String anIdentifier);
}
