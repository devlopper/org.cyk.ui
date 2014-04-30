package org.cyk.ui.api.editor.input;

import java.lang.reflect.Field;

import org.cyk.ui.api.component.UIComponent;
import org.cyk.ui.api.editor.EditorInputs;

public interface UIInputComponent<VALUE_TYPE> extends UIComponent<VALUE_TYPE> {

	EditorInputs<?, ?, ?, ?> getContainerForm();
	
	String getLabel();
	
	String getDescription();

	Object getObject();
	
	Field getField();

	Boolean getRequired();
	
	String getRequiredMessage();
	
	Boolean getReadOnly();
	
	void setReadOnly(Boolean value);
	
	String getReadOnlyValue();
	
	void setReadOnlyValue(String aValue);
	
	String getValidatorId();
	
	String getValidationGroupClass();
	
	void updateValue() throws Exception;
	
	//void afterChildFormUpdated();
}