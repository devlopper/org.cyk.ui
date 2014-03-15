package org.cyk.ui.web.api.form.input;

import org.cyk.ui.api.component.UIComponent;
import org.cyk.ui.web.api.CascadeStyleSheet;

public interface WebUIIComponent<VALUE_TYPE> extends UIComponent<VALUE_TYPE> {

	public abstract CascadeStyleSheet getCascadeStyleSheet();

}