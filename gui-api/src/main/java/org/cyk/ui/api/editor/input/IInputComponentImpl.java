package org.cyk.ui.api.editor.input;


public interface IInputComponentImpl<VALUE_TYPE> extends UInputComponentModel<VALUE_TYPE> {
	
	void updateValue() throws Exception;
	
	void afterChildFormUpdated();
	
}