package org.cyk.ui.web.primefaces.compositecomponent;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

import org.cyk.ui.web.primefaces.PrimefacesFormContainer;

@FacesComponent(value="org.cyk.ui.primefaces.Form")
public class FormCompositeComponent extends UINamingContainer {

    enum PropertyKeys {
        value
    }

    public PrimefacesFormContainer getValue() {
        return (PrimefacesFormContainer) getStateHelper().eval(PropertyKeys.value);
    }

    public void setValue(PrimefacesFormContainer form) {
        getStateHelper().put(PropertyKeys.value, form);
    } 

}