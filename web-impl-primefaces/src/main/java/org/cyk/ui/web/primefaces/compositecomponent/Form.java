package org.cyk.ui.web.primefaces.compositecomponent;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.model.SelectItem;

import org.cyk.ui.api.form.IForm;
import org.cyk.ui.api.form.IFormContainer;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;

@FacesComponent(value="org.cyk.ui.primefaces.Form")
public class Form extends UINamingContainer {

    enum PropertyKeys {
        value
    }

    @SuppressWarnings("unchecked")
	public IFormContainer<DynaFormModel, DynaFormLabel, DynaFormControl, SelectItem> getValue() {
        return (IFormContainer<DynaFormModel, DynaFormLabel, DynaFormControl, SelectItem>) getStateHelper().eval(PropertyKeys.value);
    }

    public void setValue(IForm<DynaFormModel, DynaFormLabel, DynaFormControl, SelectItem> form) {
        getStateHelper().put(PropertyKeys.value, form);
    }

 

}