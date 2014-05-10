package org.cyk.ui.web.api.form.input;

import org.cyk.ui.api.component.UIInputOutputComponent;
import org.cyk.ui.web.api.CascadeStyleSheet;

public interface WebUIIComponent<VALUE_TYPE> extends UIInputOutputComponent<VALUE_TYPE> {

	public abstract CascadeStyleSheet getCascadeStyleSheet();

}