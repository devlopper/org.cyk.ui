package org.cyk.ui.api.data.collector.control;

import java.lang.reflect.Field;

public interface Input<VALUE_TYPE,MODEL, ROW, LABEL, CONTROL, CHOICE_ITEM> extends Control<MODEL, ROW, LABEL, CONTROL, CHOICE_ITEM>  {

	public enum MessageLocation{TOP,RIGHT}
	
	String getLabel();
	void setLabel(String aLabel);
	
	String getDescription();
	void setDescription(String aDescription);
	
	Boolean getReadOnly();
	void setReadOnly(Boolean aReadOnly);
	
	String getReadOnlyValue();
	void setReadOnlyValue(String aReadOnlyValue);
	
	Boolean getKeepShowingInputOnReadOnly();
	void setKeepShowingInputOnReadOnly(Boolean value);
	
	Object getObject();
	void setObject(Object anObject);
	
	Field getField();
	void setField(Field aField);
	
	VALUE_TYPE getValue();
	void setValue(VALUE_TYPE aValue);
	
	MessageLocation getMessageLocation();
	void setMessageLocation(MessageLocation aMessageLocation);
	
	Boolean getRequired();
	void setRequired(Boolean aValue);
	
	String getRequiredMessage();
	void setRequiredMessage(String aRequiredMessage);
	
	Boolean getDisabled();
	void setDisabled(Boolean aValue);
		
	void applyValueToField() throws IllegalAccessException;
	
	VALUE_TYPE getValueToApply();

	
}
