package org.cyk.ui.web.primefaces.data.collector.form;

import java.io.Serializable;

import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.web.api.data.collector.form.AbstractWebForm;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

public class Form<DATA> extends AbstractWebForm<DATA, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl> implements Serializable {

	private static final long serialVersionUID = -8473077267633574874L;

	@Override
	public FormData<DATA> createFormData() {
		FormData<DATA> formData = new FormData<>();
		formDatas.push(formData);
		children.add(formData);
		return formData;
	}

	@Override
	public Boolean validate(UICommand command, Object parameter) {
		return null;
	}




}
