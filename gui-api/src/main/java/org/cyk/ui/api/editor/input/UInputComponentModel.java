package org.cyk.ui.api.editor.input;

import java.lang.reflect.Field;

import org.cyk.ui.api.component.UIInputOutputComponent;

@Deprecated
public interface UInputComponentModel<VALUE_TYPE> extends UIInputOutputComponent<VALUE_TYPE> {

	
	String getLabel();

	Object getObject();
	
	Field getField();

	Boolean getRequired();

	String getRequiredMessage();
	
	Boolean getReadOnly();
	
	String getValidatorId();
	
	String getValidationGroupClass();
	
	
}