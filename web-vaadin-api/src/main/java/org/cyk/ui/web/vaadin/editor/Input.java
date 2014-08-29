package org.cyk.ui.web.vaadin.editor;

import java.io.Serializable;
import java.lang.reflect.Field;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.editor.input.AbstractInputComponent;
import org.cyk.ui.api.editor.input.UIInputComponent;
import org.cyk.utility.common.annotation.UIField;

@Getter @Setter
public class Input extends AbstractInputComponent<Object> implements Serializable {

	private static final long serialVersionUID = -7887654678590867794L;
	
	private UIInputComponent<?> __input__;
	
	public Input(Field aField, Class<?> fieldType, UIField annotation, Object anObject) {
		super(aField, fieldType, annotation, anObject);
	}
	
	public Input(org.cyk.ui.web.vaadin.editor.EditorInputs editorInputs,UIInputComponent<?> input) {
		super(input.getField(),input.getFieldType(),input.getAnnotation(),input.getObject());
		__input__ = input;
		label = input.getLabel();
		description = input.getDescription();
		requiredMessage = input.getRequiredMessage();
		readOnly = input.getReadOnly();
		required = input.getRequired();
		field = input.getField();
		fieldType = input.getFieldType();
		object = input.getObject();
		value = input.getValue();
		annotation = input.getAnnotation();
		this.editorInputs = editorInputs;
		this.validationGroupClass = input.getValidationGroupClass();
		width = input.getWidth();
		height = input.getHeight();
	}

}
