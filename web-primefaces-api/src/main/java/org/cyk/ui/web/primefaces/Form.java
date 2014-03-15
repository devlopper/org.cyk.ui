package org.cyk.ui.web.primefaces;

import javax.faces.model.SelectItem;

import org.cyk.ui.api.form.UIFormData;
import org.cyk.ui.web.api.form.AbstractWebForm;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;

public class Form extends AbstractWebForm<DynaFormModel,DynaFormLabel,DynaFormControl> {

	private static final long serialVersionUID = -2915809915934469649L;
	
	@Override
	public UIFormData<DynaFormModel, DynaFormLabel, DynaFormControl, SelectItem> createFormData() {
		return new FormData();
	}

}
