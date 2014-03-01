package org.cyk.ui.web.api.component;

import java.io.Serializable;

import org.cyk.ui.api.component.AbstractComponent;

import lombok.Getter;

@Getter
public abstract class AbstractWebComponent<VALUE_TYPE> extends AbstractComponent<VALUE_TYPE> implements Serializable, IWebComponent<VALUE_TYPE> {

	private static final long serialVersionUID = 6386648827377414199L;

	protected CascadeStyleSheet cascadeStyleSheet;
	
}
