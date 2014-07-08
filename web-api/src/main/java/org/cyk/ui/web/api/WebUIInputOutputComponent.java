package org.cyk.ui.web.api;

import org.cyk.ui.api.component.UIInputOutputComponent;

public interface WebUIInputOutputComponent<VALUE_TYPE> extends UIInputOutputComponent<VALUE_TYPE> {

	CascadeStyleSheet getCascadeStyleSheet();
	
	CascadeStyleSheet getReadOnlyValueCascadeStyleSheet();

}