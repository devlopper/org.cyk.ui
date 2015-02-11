package org.cyk.ui.web.primefaces.data.collector.control;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.data.collector.control.Input;
import org.cyk.ui.web.api.CascadeStyleSheet;
import org.cyk.ui.web.api.data.collector.control.WebInput;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public abstract class AbstractInput<VALUE_TYPE> extends AbstractControl implements Input<VALUE_TYPE,DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl,SelectItem>,
	WebInput<DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl>, Serializable {

	private static final long serialVersionUID = 5671513590779656492L;

	protected Object object;
	protected String label,readOnlyValue,description,requiredMessage;
	protected CascadeStyleSheet readOnlyValueCss = new CascadeStyleSheet(); 
	protected Field field;
	protected VALUE_TYPE value;
	protected Boolean required,readOnly;
	protected MessageLocation messageLocation = MessageLocation.TOP;
	
	@Override
	public void applyValueToField() throws IllegalAccessException{
		FieldUtils.writeField(field, object, getValueToApply(), Boolean.TRUE);
	}
	
	@Override
	public VALUE_TYPE getValueToApply(){
		return value;
	}

	@Override
	public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {}
	
	@Override
	public Converter getConverter() {
		return null;
	}
	
	protected void validationException(String messageId){
		new ValidatorException(new FacesMessage(UIManager.getInstance().text(messageId)));
	}
	
}
