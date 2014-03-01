package org.cyk.ui.web.primefaces.compositecomponent;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

import org.cyk.ui.api.form.IForm;

@FacesComponent(value="org.cyk.ui.primefaces.Form")
public class Form extends UINamingContainer {

    enum PropertyKeys {
        value
    }

    public IForm getValue() {
        return (IForm) getStateHelper().eval(PropertyKeys.value);
    }

    public void setValue(IForm form) {
        getStateHelper().put(PropertyKeys.value, form);
    }

 

}