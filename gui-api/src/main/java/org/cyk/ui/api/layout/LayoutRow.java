package org.cyk.ui.api.layout;

import java.util.Collection;

import org.cyk.ui.api.component.UIComponent;

public interface LayoutRow {
	
	Collection<UIComponent<?>> getComponents();

	LayoutRow add(UIComponent<?> component);
}
