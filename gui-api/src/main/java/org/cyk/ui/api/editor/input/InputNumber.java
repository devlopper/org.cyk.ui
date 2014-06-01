package org.cyk.ui.api.editor.input;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.cyk.utility.common.annotation.UIField;

public class InputNumber extends AbstractInputComponent<Number> implements Serializable, UIInputNumber {

	private static final long serialVersionUID = -7367234616039323949L;
 
	public InputNumber(Field aField,Class<?> fieldType,UIField annotation,Object anObject) {
		super(aField,fieldType,annotation,anObject);
	}
	
	/*
	 * 
	 * 	@Override
	public Converter getConverter() {
		try {
			return (Converter) Class.forName("javax.faces.convert."+
					(field.getType().getSimpleName().equals("int")?"Integer":
							Character.toUpperCase(field.getType().getSimpleName().charAt(0))+field.getType().getSimpleName().substring(1))
							+"Converter").newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	 * 
	 */
	
}
