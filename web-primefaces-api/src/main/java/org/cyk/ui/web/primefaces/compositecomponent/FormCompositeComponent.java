package org.cyk.ui.web.primefaces.compositecomponent;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

import org.cyk.ui.web.primefaces.PrimefacesForm;

@FacesComponent(value="org.cyk.ui.primefaces.Form")
public class FormCompositeComponent extends UINamingContainer {

    enum PropertyKeys {
        value
    }

    public PrimefacesForm getValue() {
        return (PrimefacesForm) getStateHelper().eval(PropertyKeys.value);
    }

    public void setValue(PrimefacesForm form) {
        getStateHelper().put(PropertyKeys.value, form);
    } 

}