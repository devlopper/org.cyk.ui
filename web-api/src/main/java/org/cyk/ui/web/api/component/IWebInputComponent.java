package org.cyk.ui.web.api.component;

import javax.faces.convert.Converter;

import org.cyk.ui.api.component.input.IInputComponent;


public interface IWebInputComponent<VALUE_TYPE> extends IWebComponent<VALUE_TYPE>,IInputComponent<VALUE_TYPE> {

	Converter getConverter();
	
}