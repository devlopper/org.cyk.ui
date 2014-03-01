package org.cyk.ui.web.api.component;

import org.cyk.ui.api.component.IComponent;

public interface IWebComponent<VALUE_TYPE> extends IComponent<VALUE_TYPE> {

	public abstract CascadeStyleSheet getCascadeStyleSheet();

}