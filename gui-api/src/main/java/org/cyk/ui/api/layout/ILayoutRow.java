package org.cyk.ui.api.layout;

import java.util.Collection;

import org.cyk.ui.api.component.IComponent;

public interface ILayoutRow {
	
	Collection<IComponent<?>> getComponents();

	ILayoutRow add(IComponent<?> component);
}
