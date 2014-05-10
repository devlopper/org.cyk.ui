package org.cyk.ui.web.api;

import java.io.Serializable;

import lombok.Getter;

import org.cyk.ui.api.component.output.UIOutputComponent;
import org.cyk.ui.web.api.form.input.WebUIOutputComponent;

@Getter
public class AbstractWebOutputComponent<VALUE_TYPE> extends AbstractWebInputOutputComponent<VALUE_TYPE> implements Serializable, WebUIOutputComponent<VALUE_TYPE> {

	private static final long serialVersionUID = 6386648827377414199L;

	public AbstractWebOutputComponent(UIOutputComponent<VALUE_TYPE> output) {
		
	}
	
}
