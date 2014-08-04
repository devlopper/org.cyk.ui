package org.cyk.ui.web.primefaces.compositecomponent;

import javax.faces.component.UINamingContainer;

public abstract class AbstractCompositeComponent<T> extends UINamingContainer {

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