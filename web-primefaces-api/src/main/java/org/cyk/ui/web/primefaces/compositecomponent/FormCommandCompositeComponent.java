package org.cyk.ui.web.primefaces.compositecomponent;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

import org.cyk.ui.web.primefaces.PrimefacesFormCommand;

@FacesComponent(value="org.cyk.ui.primefaces.FormCommand")
public class FormCommandCompositeComponent extends UINamingContainer {

    enum PropertyKeys {
        value
    }

    public PrimefacesFormCommand getValue() {
        return (PrimefacesFormCommand) getStateHelper().eval(PropertyKeys.value);
    }

    public void setValue(PrimefacesFormCommand command) {
        getStateHelper().put(PropertyKeys.value, command);
    } 

}