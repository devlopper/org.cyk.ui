package org.cyk.ui.api.component.output;

import java.io.Serializable;

import org.cyk.ui.api.component.AbstractInputOutputComponent;

public abstract class AbstractOutputComponent<VALUE_TYPE> extends AbstractInputOutputComponent<VALUE_TYPE> implements Serializable, UIOutputComponent<VALUE_TYPE> {

	private static final long serialVersionUID = -1941413441310418139L;

	public AbstractOutputComponent(Class<VALUE_TYPE> aClass,VALUE_TYPE aValue) {
		super(aClass, aValue);
		
	}
	
}
