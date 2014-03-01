package org.cyk.ui.api.component.input;

import java.io.Serializable;
import java.lang.reflect.Field;

public class InputText extends AbstractInputComponent<String> implements Serializable, IInputText {

	private static final long serialVersionUID = -7367234616039323949L;

	public InputText(String aLabel, Field aField,Object anObject) {
		super(aLabel, aField,anObject);
	}
	
}
