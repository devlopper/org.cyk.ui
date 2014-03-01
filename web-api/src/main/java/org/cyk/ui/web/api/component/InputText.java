package org.cyk.ui.web.api.component;

import java.io.Serializable;

import lombok.Getter;

import org.cyk.ui.api.component.input.IInputComponent;

@Getter
public class InputText extends AbstractWebInputComponent<String> implements IWebInputText,Serializable  {

	private static final long serialVersionUID = 7029658406107605595L;

	private String filterMask;
	
	public InputText(IInputComponent<String> input) {
		super(input);
	}
	
}
