package org.cyk.ui.web.api.editor.input;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.validator.ValidatorException;

import org.cyk.ui.api.editor.input.UIInputComponent;
import org.cyk.ui.web.api.WebUIInputOutputComponent;


public interface WebUIInputComponent<VALUE_TYPE> extends WebUIInputOutputComponent<VALUE_TYPE>,UIInputComponent<VALUE_TYPE> {

	Converter getConverter();
	
	void validate(FacesContext facesContext,UIComponent uiComponent,Object value) throws ValidatorException;
	
	String getTemplateFile();
	String getTemplateFileAtRight();
	String getTemplateFileAtTop();
}