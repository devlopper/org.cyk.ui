package org.cyk.ui.api.editor.input;

import java.lang.reflect.Field;

import org.cyk.ui.api.component.UIInputOutputComponent;
import org.cyk.ui.api.editor.EditorInputs;

@Deprecated
public interface UInputComponentModel<VALUE_TYPE> extends UIInputOutputComponent<VALUE_TYPE> {

	EditorInputs<?, ?, ?, ?> getContainerForm();
	
	String getLabel();

	Object getObject();
	
	Field getField();

	Boolean getRequired();

	String getRequiredMessage();
	
	Boolean getReadOnly();
	
	String getValidatorId();
	
	String getValidationGroupClass();
	
	
}