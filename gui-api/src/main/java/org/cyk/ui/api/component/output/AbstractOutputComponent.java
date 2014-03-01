package org.cyk.ui.api.component.output;

import java.io.Serializable;

import org.cyk.ui.api.component.AbstractComponent;

public abstract class AbstractOutputComponent<VALUE_TYPE> extends AbstractComponent<VALUE_TYPE> implements Serializable, IOutputComponent<VALUE_TYPE> {

	private static final long serialVersionUID = -1941413441310418139L;

	public AbstractOutputComponent(Class<VALUE_TYPE> aClass,VALUE_TYPE aValue) {
		super(aClass, aValue);
		
	}
	
}
