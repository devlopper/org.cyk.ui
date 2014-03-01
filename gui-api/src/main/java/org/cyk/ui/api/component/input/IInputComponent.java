package org.cyk.ui.api.component.input;

import java.lang.reflect.Field;

import org.cyk.ui.api.component.IComponent;

public interface IInputComponent<VALUE_TYPE> extends IComponent<VALUE_TYPE> {

	//IOutputLabel getLabel();
	
	String getLabel();

	Object getObject();
	
	Field getField();

	Boolean getRequired();

	String getRequiredMessage();
	
	Boolean getReadOnly();
	
	String getValidatorId();
	
	String getValidationGroupClass();

}