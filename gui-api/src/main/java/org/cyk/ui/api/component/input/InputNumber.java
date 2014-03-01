package org.cyk.ui.api.component.input;

import java.io.Serializable;
import java.lang.reflect.Field;

public class InputNumber extends AbstractInputComponent<Number> implements Serializable, IInputNumber {

	private static final long serialVersionUID = -7367234616039323949L;
 
	public InputNumber(String aLabel, Field aField,Object anObject) {
		super(aLabel, aField,anObject);
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
