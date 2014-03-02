package org.cyk.ui.api.component.input;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.logging.Level;

import lombok.Getter;
import lombok.extern.java.Log;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.ui.api.annotation.FormField;
import org.cyk.ui.api.component.AbstractComponent;
import org.cyk.ui.api.form.IForm;

@Getter @Log
public abstract class AbstractInputComponent<VALUE_TYPE> extends AbstractComponent<VALUE_TYPE> implements Serializable, IInputComponent<VALUE_TYPE> {

	private static final long serialVersionUID = 438462134701637492L;

	protected IForm<?, ?, ?, ?> containerForm;
	protected String label;
	protected VALUE_TYPE value;
	protected Object object;
	protected Field field;
	protected Boolean required,readOnly;
	protected String requiredMessage,validatorId,validationGroupClass;
	
	@SuppressWarnings("unchecked")
	public AbstractInputComponent(String aLabel,Field aField,Object anObject) {
		this.label=aLabel;
		this.field=aField;
		FormField formField = aField.getAnnotation(FormField.class);
		required = formField.required();
		requiredMessage = "Valeur obligatoire";
		this.object=anObject;
		try {
			value = (VALUE_TYPE) FieldUtils.readField(field, object, Boolean.TRUE);
		} catch (Exception e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
	}
	
	
}
