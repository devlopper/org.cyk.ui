package org.cyk.ui.web.api.form.input;

import java.io.Serializable;

import lombok.Getter;

import org.cyk.ui.api.form.UIFormData;
import org.cyk.ui.api.form.input.UIInputComponent;

@Getter
public class InputText extends AbstractWebInputComponent<String> implements WebUIInputText, Serializable  {

	private static final long serialVersionUID = 7029658406107605595L;

	private String filterMask;
	
	public InputText(UIFormData<?, ?, ?, ?> containerForm,UIInputComponent<String> input) {
		super(containerForm,input);
	}
	
}
