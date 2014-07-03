package org.cyk.ui.api.editor.input;

import java.lang.reflect.Field;

import org.cyk.ui.api.component.UIInputOutputComponent;
import org.cyk.ui.api.editor.EditorInputs;
import org.cyk.utility.common.annotation.UIField;

public interface UIInputComponent<VALUE_TYPE> extends UIInputOutputComponent<VALUE_TYPE> {

	EditorInputs<?, ?, ?, ?> getEditorInputs();
	
	String getLabel();
	
	String getDescription();

	Object getObject();
	
	UIField getAnnotation();
	
	Field getField();
	
	Class<?> getFieldType();

	void setRequired(Boolean required);
	
	Boolean getRequired();
	
	String getRequiredMessage();
	
	Boolean getReadOnly();
	
	void setReadOnly(Boolean value);
	
	String getReadOnlyValue();
	
	//void setReadOnlyValue(String aValue);
	
	String getValidatorId();
	
	String getValidationGroupClass();
	
	VALUE_TYPE getValidatedValue();
	
	void setValidatedValue(VALUE_TYPE value);
	
	void updateValue() throws Exception;
	
	void updateReadOnlyValue();
	
	//void afterChildFormUpdated();
}