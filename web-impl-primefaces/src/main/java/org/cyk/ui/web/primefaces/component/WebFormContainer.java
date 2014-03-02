package org.cyk.ui.web.primefaces.component;

import javax.faces.model.SelectItem;
import javax.inject.Inject;

import lombok.Getter;

import org.cyk.ui.api.form.IForm;
import org.cyk.ui.web.api.AbstractWebFormContainer;
import org.cyk.ui.web.primefaces.controller.MyEntity;
import org.cyk.ui.web.primefaces.controller.MyEntity.MyDetails;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;

public class WebFormContainer extends AbstractWebFormContainer<DynaFormModel,DynaFormLabel,DynaFormControl> {

	private static final long serialVersionUID = -2915809915934469649L;

	@Getter 
	@Inject private WebForm form;
		
	@Override
	protected IForm<DynaFormModel, DynaFormLabel, DynaFormControl, SelectItem> mainModel() {
		MyEntity p;
		form.model(p=new MyEntity());
		p.setDetails1(new MyDetails());
		p.getDetails1().setDetailsName("Glory to the lord");
		//myForm.model(p=new MyEntity());
		//p.setName("Only you");
		form.build();
		return form;
	}

}
