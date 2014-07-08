package org.cyk.ui.web.api;

import java.io.Serializable;

import org.cyk.ui.api.component.AbstractInputOutputComponent;

import lombok.Getter;

@Getter
public abstract class AbstractWebInputOutputComponent<VALUE_TYPE> extends AbstractInputOutputComponent<VALUE_TYPE> implements Serializable, WebUIInputOutputComponent<VALUE_TYPE> {

	private static final long serialVersionUID = 6386648827377414199L;

	protected CascadeStyleSheet cascadeStyleSheet = new CascadeStyleSheet();
	protected CascadeStyleSheet readOnlyValueCascadeStyleSheet = new CascadeStyleSheet();

	
}
