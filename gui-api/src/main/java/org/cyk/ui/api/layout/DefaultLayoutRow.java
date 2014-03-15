package org.cyk.ui.api.layout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.ui.api.component.UIComponent;

public class DefaultLayoutRow implements LayoutRow,Serializable {

	private static final long serialVersionUID = -5272418768568215302L;

	private Collection<UIComponent<?>> components = new ArrayList<>();
	
	@Override
	public Collection<UIComponent<?>> getComponents() {
		return components;
	}

	@Override
	public LayoutRow add(UIComponent<?> component) {
		components.add(component);
		return this;
	}

}
