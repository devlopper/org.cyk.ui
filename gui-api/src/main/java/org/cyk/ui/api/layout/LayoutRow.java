package org.cyk.ui.api.layout;

import java.util.Collection;

import org.cyk.ui.api.component.UIInputOutputComponent;

public interface LayoutRow {
	
	Collection<UIInputOutputComponent<?>> getComponents();

	LayoutRow add(UIInputOutputComponent<?> component);
}
