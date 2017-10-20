package org.cyk.ui.web.primefaces.resources.compositecomponent;

import java.io.Serializable;

import javax.faces.component.UINamingContainer;

public abstract class AbstractCompositeComponent<T> extends UINamingContainer implements Serializable {
	private static final long serialVersionUID = 1L;

	public enum PropertyKeys {
        value
    }

    @SuppressWarnings("unchecked")
	public T getValue() {
        return (T) getStateHelper().eval(PropertyKeys.value);
    }

    public void setValue(T value) {
        getStateHelper().put(PropertyKeys.value, value);
    } 

}