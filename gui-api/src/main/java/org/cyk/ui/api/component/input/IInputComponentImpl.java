package org.cyk.ui.api.component.input;


public interface IInputComponentImpl<VALUE_TYPE> extends IInputComponentModel<VALUE_TYPE> {
	
	void updateValue() throws Exception;
	
	void afterChildFormUpdated();
	
}