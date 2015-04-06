package org.cyk.ui.api.command.menu;

import java.util.Collection;

import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.api.command.UICommandable.ViewType;

public interface UIMenu {
	
	enum RenderType{PLAIN,SLIDE,PANEL}
	
	Collection<UICommandable> getCommandables();

	UICommandable commandable(String anIdentifier);
	
	UICommandable addCommandable(String labelId,IconType iconType);
	UICommandable addCommandable(UICommandable commandable);
	UICommandable addCommandable(String labelId,IconType iconType,ViewType viewType);
	UICommandable addCommandable(String labelId,IconType iconType,Object viewId);
	
	RenderType getRenderType();
	void setRenderType(RenderType aRenderType);
}
