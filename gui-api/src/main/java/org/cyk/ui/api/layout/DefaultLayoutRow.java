package org.cyk.ui.api.layout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.ui.api.component.IComponent;

public class DefaultLayoutRow implements ILayoutRow,Serializable {

	private static final long serialVersionUID = -5272418768568215302L;

	private Collection<IComponent<?>> components = new ArrayList<>();
	
	@Override
	public Collection<IComponent<?>> getComponents() {
		return components;
	}

	@Override
	public ILayoutRow add(IComponent<?> component) {
		components.add(component);
		return this;
	}

}
