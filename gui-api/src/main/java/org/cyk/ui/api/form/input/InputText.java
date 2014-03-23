package org.cyk.ui.api.form.input;

import java.io.Serializable;
import java.lang.reflect.Field;

import lombok.Getter;

public class InputText extends AbstractInputComponent<String> implements Serializable, UIInputText {

	private static final long serialVersionUID = -7367234616039323949L;

	@Getter private Integer rowCount,columnCount;
	
	public InputText(String aLabel, Field aField,Object anObject) {
		super(aLabel, aField,anObject);
		columnCount = annotation.textColumnCount();
		if(annotation.textArea()){
			rowCount = annotation.textRowCount();
		}else{
			rowCount=1;
		}
	}
	
	
	
}
