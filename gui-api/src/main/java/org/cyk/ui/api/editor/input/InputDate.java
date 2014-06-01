package org.cyk.ui.api.editor.input;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;

import org.cyk.utility.common.annotation.UIField;

public class InputDate extends AbstractInputComponent<Date> implements Serializable, UIInputDate {

	private static final long serialVersionUID = -7367234616039323949L;

	public InputDate(Field aField,Class<?> fieldType,UIField annotation,Object anObject) {
		super(aField,fieldType,annotation,anObject);
	}
	
}
