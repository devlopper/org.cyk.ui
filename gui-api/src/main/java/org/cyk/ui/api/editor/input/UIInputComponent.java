package org.cyk.ui.api.editor.input;

import java.lang.reflect.Field;

import org.cyk.system.root.business.api.validation.ValidationPolicy;
import org.cyk.ui.api.component.UIInputOutputComponent;
import org.cyk.ui.api.editor.EditorInputs;
import org.cyk.utility.common.annotation.UIField;

public interface UIInputComponent<VALUE_TYPE> extends UIInputOutputComponent<VALUE_TYPE> {

	EditorInputs<?, ?, ?, ?> getEditorInputs();
	
	ValidationPolicy getValidationPolicy();
	
	void setValidationPolicy(ValidationPolicy aValidationPolicy);
	
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
	
	void validate(VALUE_TYPE aValue);
	
	VALUE_TYPE getValidatedValue();
	
	void setValidatedValue(VALUE_TYPE value);
	
	void updateValue() throws Exception;
	
	void updateReadOnlyValue();
	
	//void afterChildFormUpdated();
}