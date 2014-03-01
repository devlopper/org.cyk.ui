package org.cyk.ui.web.api.component;

import java.io.Serializable;

import lombok.Getter;

import org.cyk.ui.api.component.output.IOutputComponent;

@Getter
public class AbstractWebOutputComponent<VALUE_TYPE> extends AbstractWebComponent<VALUE_TYPE> implements Serializable, IWebOutputComponent<VALUE_TYPE> {

	private static final long serialVersionUID = 6386648827377414199L;

	public AbstractWebOutputComponent(IOutputComponent<VALUE_TYPE> output) {
		
	}
	
}
