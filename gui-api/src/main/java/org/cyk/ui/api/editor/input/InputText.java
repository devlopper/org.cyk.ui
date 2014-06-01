package org.cyk.ui.api.editor.input;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.cyk.utility.common.annotation.UIField;

import lombok.Getter;

public class InputText extends AbstractInputComponent<String> implements Serializable, UIInputText {

	private static final long serialVersionUID = -7367234616039323949L;

	@Getter private Integer rowCount,columnCount;
	
	public InputText(Field aField,Class<?> fieldType,UIField annotation,Object anObject) {
		super(aField,fieldType,annotation,anObject);
		columnCount = annotation.textColumnCount();
		if(annotation.textArea()){
			rowCount = annotation.textRowCount();
		}else{
			rowCount=1;
		}
	}
	
	
	
}
