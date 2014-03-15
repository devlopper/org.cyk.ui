package org.cyk.ui.api.form.input;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;

public class InputDate extends AbstractInputComponent<Date> implements Serializable, UIInputDate {

	private static final long serialVersionUID = -7367234616039323949L;

	public InputDate(String aLabel, Field aField,Object anObject) {
		super(aLabel, aField,anObject);
	}
	
}
