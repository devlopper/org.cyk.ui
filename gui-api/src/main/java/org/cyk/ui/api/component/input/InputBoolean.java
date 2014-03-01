package org.cyk.ui.api.component.input;

import java.io.Serializable;
import java.lang.reflect.Field;

public class InputBoolean extends AbstractInputSelectOne<Boolean> implements Serializable, IInputBoolean {

	private static final long serialVersionUID = -7367234616039323949L;

	public InputBoolean(String aLabel, Field aField,Object anObject) {
		super(aLabel, aField,anObject,null);
		addItem(new DefaultSelectItem("OUI",true));
		addItem(new DefaultSelectItem("NON",false));
	}
	
}
