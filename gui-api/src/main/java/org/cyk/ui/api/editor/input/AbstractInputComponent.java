package org.cyk.ui.api.editor.input;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.logging.Level;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.component.AbstractInputOutputComponent;
import org.cyk.ui.api.editor.EditorInputs;
import org.cyk.utility.common.annotation.UIField;
import org.cyk.utility.common.validation.Client;

@Getter @Setter @Log
public abstract class AbstractInputComponent<VALUE_TYPE> extends AbstractInputOutputComponent<VALUE_TYPE> implements Serializable, UIInputComponent<VALUE_TYPE> {

	private static final long serialVersionUID = 438462134701637492L;

	protected EditorInputs<?, ?, ?, ?> editorInputs;
	protected String label,description;
	protected VALUE_TYPE value;
	protected Object object;
	protected Field field;
	protected Boolean required,readOnly;
	protected String requiredMessage,validatorId,validationGroupClass=Client.class.getName(),readOnlyValue;
	
	protected UIField annotation;
	
	@SuppressWarnings("unchecked")
	public AbstractInputComponent(String aLabel,Field aField,Object anObject) {
		this.label=aLabel;
		this.field=aField;
		annotation = aField.getAnnotation(UIField.class);
		this.description = annotation.description();
		required = annotation.required();
		requiredMessage = "Valeur obligatoire";
		this.object=anObject;
		try {
			value = (VALUE_TYPE) FieldUtils.readField(field, object, Boolean.TRUE);
		} catch (Exception e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
		readOnlyValue = value==null?"":value.toString();
	}
	
	@Override
	public void updateValue() throws Exception {
		throw new IllegalArgumentException("Must not call this method on this object");
	}
	
	public static UIInputComponent<?> create(UIInputComponent<?> anInputComponent){
		return UIManager.COMPONENT_CREATE_METHOD.execute(anInputComponent);
	}
	
	

	
}
