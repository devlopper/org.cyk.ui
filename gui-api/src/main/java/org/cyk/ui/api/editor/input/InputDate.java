package org.cyk.ui.api.editor.input;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.UIManager;
import org.cyk.utility.common.annotation.UIField;

@Getter @Setter
public class InputDate extends AbstractInputComponent<Date> implements Serializable, UIInputDate {

	private static final long serialVersionUID = -7367234616039323949L;

	private String pattern;
	
	public InputDate(Field aField,Class<?> fieldType,UIField annotation,Object anObject) {
		super(aField,fieldType,annotation,anObject);
		Temporal temporal = aField.getAnnotation(Temporal.class);
		if(temporal==null || TemporalType.TIMESTAMP.equals(temporal.value()))
			pattern=UIManager.getInstance().text("string.format.pattern.date")+" "+UIManager.getInstance().text("string.format.pattern.time");
		else if(TemporalType.DATE.equals(temporal.value()))
			pattern=UIManager.getInstance().text("string.format.pattern.date");
		else if(TemporalType.TIME.equals(temporal.value()))
			pattern=UIManager.getInstance().text("string.format.pattern.time");
	}
	
}
