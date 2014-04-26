package org.cyk.ui.api.form.input;

import java.lang.reflect.Field;

import org.cyk.ui.api.component.UIComponent;
import org.cyk.ui.api.form.UISubForm;

public interface UInputComponentModel<VALUE_TYPE> extends UIComponent<VALUE_TYPE> {

	UISubForm<?, ?, ?, ?> getContainerForm();
	
	String getLabel();

	Object getObject();
	
	Field getField();

	Boolean getRequired();

	String getRequiredMessage();
	
	Boolean getReadOnly();
	
	String getValidatorId();
	
	String getValidationGroupClass();
	
	
}