package org.cyk.ui.api.form.input;


public interface IInputComponentImpl<VALUE_TYPE> extends UInputComponentModel<VALUE_TYPE> {
	
	void updateValue() throws Exception;
	
	void afterChildFormUpdated();
	
}