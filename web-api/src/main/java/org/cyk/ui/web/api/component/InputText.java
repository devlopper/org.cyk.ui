package org.cyk.ui.web.api.component;

import java.io.Serializable;

import lombok.Getter;

import org.cyk.ui.api.component.input.IInputComponent;
import org.cyk.ui.api.form.IForm;

@Getter
public class InputText extends AbstractWebInputComponent<String> implements IWebInputText, Serializable  {

	private static final long serialVersionUID = 7029658406107605595L;

	private String filterMask;
	
	public InputText(IForm<?, ?, ?, ?> containerForm,IInputComponent<String> input) {
		super(containerForm,input);
	}
	
}
