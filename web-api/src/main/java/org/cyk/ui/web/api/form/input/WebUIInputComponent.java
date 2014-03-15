package org.cyk.ui.web.api.form.input;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.validator.ValidatorException;

import org.cyk.ui.api.form.input.UIInputComponent;


public interface WebUIInputComponent<VALUE_TYPE> extends WebUIIComponent<VALUE_TYPE>,UIInputComponent<VALUE_TYPE> {

	Converter getConverter();
	
	void validate(FacesContext facesContext,UIComponent uiComponent,Object value) throws ValidatorException;
	
}