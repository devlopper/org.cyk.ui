package org.cyk.ui.web.api.form.input;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.validator.ValidatorException;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.ui.api.editor.EditorInputs;
import org.cyk.ui.api.editor.input.UIInputComponent;
import org.cyk.ui.web.api.AbstractWebComponent;
import org.cyk.utility.common.annotation.UIField;

@Getter @Setter
public class AbstractWebInputComponent<VALUE_TYPE> extends AbstractWebComponent<VALUE_TYPE> implements Serializable, WebUIInputComponent<VALUE_TYPE> {

	private static final long serialVersionUID = 6386648827377414199L;
	
	protected EditorInputs<?, ?, ?, ?> containerForm;
	protected String label,requiredMessage,validatorId,validationGroupClass,readOnlyValue,description;
	protected Boolean readOnly,required;
	protected Field field;
	protected Converter converter;
	protected Object object; 
	protected UIField annotation;

	public AbstractWebInputComponent(EditorInputs<?, ?, ?, ?> containerForm,UIInputComponent<VALUE_TYPE> input) {
		label = input.getLabel();
		description = input.getDescription();
		requiredMessage = input.getRequiredMessage();
		readOnly = input.getReadOnly();
		required = input.getRequired();
		field = input.getField();
		object = input.getObject();
		value = input.getValue();
		annotation = field.getAnnotation(UIField.class);
		this.containerForm = containerForm;
		readOnlyValue = input.getReadOnlyValue();
	}
	
	public String getReadOnlyValue(){
		Object value = commonUtils.readField(object, field, false);
		return uiManager.toString(value);
	}
	
	public void validate(FacesContext facesContext,UIComponent uiComponent,Object value) throws ValidatorException{
		//Dynamically find validation logic
		//System.out.println("AbstractWebInputComponent.validate() : "+value);
		
	}

	@Override
	public void updateValue() throws Exception {
		//System.out.println(field.getName()+" - "+value);
		FieldUtils.writeField(field, object, value, true);
	}
		
}
