package org.cyk.ui.web.api.component;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.validator.ValidatorException;

import lombok.Getter;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.ui.api.annotation.FormField;
import org.cyk.ui.api.component.input.IInputComponent;
import org.cyk.ui.api.form.IForm;

@Getter
public class AbstractWebInputComponent<VALUE_TYPE> extends AbstractWebComponent<VALUE_TYPE> implements Serializable, IWebInputComponent<VALUE_TYPE> {

	private static final long serialVersionUID = 6386648827377414199L;
	
	protected IForm<?, ?, ?, ?> containerForm;
	protected String label,requiredMessage,validatorId,validationGroupClass;
	protected Boolean readOnly,required;
	protected Field field;
	protected Converter converter;
	protected Object object;
	protected FormField formField;

	public AbstractWebInputComponent(IForm<?, ?, ?, ?> containerForm,IInputComponent<VALUE_TYPE> input) {
		label = input.getLabel();
		requiredMessage = input.getRequiredMessage();
		readOnly = input.getReadOnly();
		required = true;//input.getRequired();
		field = input.getField();
		object = input.getObject();
		value = input.getValue();
		formField = field.getAnnotation(FormField.class);
		this.containerForm = containerForm;
	}
	
	public void validate(FacesContext facesContext,UIComponent uiComponent,Object value) throws ValidatorException{
		//Dynamically find validation logic
		//System.out.println("AbstractWebInputComponent.validate() : "+value);
		
	}

	@Override
	public void updateValue() throws Exception {
		FieldUtils.writeField(field, object, value, true);
	}
		
}
