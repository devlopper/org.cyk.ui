package org.cyk.ui.web.primefaces.compositecomponent;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

import org.cyk.ui.web.primefaces.PrimefacesEditor;

@FacesComponent(value="org.cyk.ui.primefaces.Form")
public class FormCompositeComponent extends UINamingContainer {

    enum PropertyKeys {
        value
    }

    public PrimefacesEditor getValue() {
        return (PrimefacesEditor) getStateHelper().eval(PropertyKeys.value);
    }

    public void setValue(PrimefacesEditor form) {
        getStateHelper().put(PropertyKeys.value, form);
    } 

}