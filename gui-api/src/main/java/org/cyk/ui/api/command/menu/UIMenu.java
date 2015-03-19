package org.cyk.ui.api.command.menu;

import java.util.Collection;

import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.IconType;

public interface UIMenu {
	
	enum RenderType{PLAIN,SLIDE,PANEL}
	
	Collection<UICommandable> getCommandables();

	UICommandable commandable(String anIdentifier);
	
	UICommandable addCommandable(String labelId,IconType iconType);
	
	UICommandable addCommandable(UICommandable commandable);
	
	RenderType getRenderType();
	void setRenderType(RenderType aRenderType);
}
