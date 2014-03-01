package org.cyk.ui.web.api.component;

import java.io.Serializable;

import lombok.Getter;

import org.cyk.ui.api.component.input.IInputNumber;

@Getter
public class InputNumber extends AbstractWebInputComponent<Number> implements IWebInputNumber,Serializable  {

	private static final long serialVersionUID = 7029658406107605595L;

	public InputNumber(IInputNumber input) {
		super(input);
	}
	
}
