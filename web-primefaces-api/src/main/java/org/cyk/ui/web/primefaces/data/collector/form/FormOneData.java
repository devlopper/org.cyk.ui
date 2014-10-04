package org.cyk.ui.web.primefaces.data.collector.form;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.web.api.data.collector.form.AbstractWebFormOneData;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

@Getter @Setter
public class FormOneData<DATA> extends AbstractWebFormOneData<DATA, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl> implements Serializable {

	private static final long serialVersionUID = -8473077267633574874L;

	{
		templateId = "/org.cyk.ui.web.primefaces/template/form/one/default.xhtml";
	} 
	
	@Override
	public FormData<DATA> createFormData() {
		FormData<DATA> formData = new FormData<>();
		formData.setForm(this);
		formDatas.push(formData);
		children.add(formData);
		return formData;
	}

}
