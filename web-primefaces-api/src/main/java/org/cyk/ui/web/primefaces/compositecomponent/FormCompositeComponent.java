package org.cyk.ui.web.primefaces.compositecomponent;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.model.SelectItem;

import org.cyk.ui.api.form.UIFormData;
import org.cyk.ui.api.form.UIFormContainer;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;

@FacesComponent(value="org.cyk.ui.primefaces.Form")
public class FormCompositeComponent extends UINamingContainer {

    enum PropertyKeys {
        value
    }

    @SuppressWarnings("unchecked")
	public UIFormContainer<DynaFormModel, DynaFormLabel, DynaFormControl, SelectItem> getValue() {
        return (UIFormContainer<DynaFormModel, DynaFormLabel, DynaFormControl, SelectItem>) getStateHelper().eval(PropertyKeys.value);
    }

    public void setValue(UIFormData<DynaFormModel, DynaFormLabel, DynaFormControl, SelectItem> form) {
        getStateHelper().put(PropertyKeys.value, form);
    } 

}