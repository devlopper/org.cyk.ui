package org.cyk.ui.api.form.input;

import java.io.Serializable;
import java.lang.reflect.Field;

public class InputText extends AbstractInputComponent<String> implements Serializable, UIInputText {

	private static final long serialVersionUID = -7367234616039323949L;

	public InputText(String aLabel, Field aField,Object anObject) {
		super(aLabel, aField,anObject);
	}
	
}
