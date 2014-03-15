package org.cyk.ui.web.api;

import java.io.Serializable;

import org.cyk.ui.api.component.AbstractComponent;
import org.cyk.ui.web.api.form.input.WebUIIComponent;

import lombok.Getter;

@Getter
public abstract class AbstractWebComponent<VALUE_TYPE> extends AbstractComponent<VALUE_TYPE> implements Serializable, WebUIIComponent<VALUE_TYPE> {

	private static final long serialVersionUID = 6386648827377414199L;

	protected CascadeStyleSheet cascadeStyleSheet;
	
}
