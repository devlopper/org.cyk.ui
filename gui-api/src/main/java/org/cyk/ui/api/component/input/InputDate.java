package org.cyk.ui.api.component.input;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;

public class InputDate extends AbstractInputComponent<Date> implements Serializable, IInputDate {

	private static final long serialVersionUID = -7367234616039323949L;

	public InputDate(String aLabel, Field aField,Object anObject) {
		super(aLabel, aField,anObject);
	}
	
}
