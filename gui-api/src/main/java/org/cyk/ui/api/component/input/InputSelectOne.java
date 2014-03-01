package org.cyk.ui.api.component.input;

import java.io.Serializable;
import java.lang.reflect.Field;

public class InputSelectOne extends AbstractInputSelectOne<Object> implements Serializable, IInputSelectOne<Object> {

	private static final long serialVersionUID = -7367234616039323949L;

	public InputSelectOne(String aLabel, Field aField,Object anObject) {
		super(aLabel, aField,anObject,null);
		if(aField.getType().isEnum())
			for(Object value : aField.getType().getEnumConstants())
				addItem(new DefaultSelectItem(value.toString(),value));
		
	}
	
}
