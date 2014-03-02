package org.cyk.ui.web.api.component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.validator.ValidatorException;

import org.cyk.ui.api.component.input.IInputComponent;


public interface IWebInputComponent<VALUE_TYPE> extends IWebComponent<VALUE_TYPE>,IInputComponent<VALUE_TYPE> {

	Converter getConverter();
	
	void validate(FacesContext facesContext,UIComponent uiComponent,Object value) throws ValidatorException;
	
}