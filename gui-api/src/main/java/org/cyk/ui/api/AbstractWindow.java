package org.cyk.ui.api;

import java.io.Serializable;

import lombok.Getter;

import org.cyk.ui.api.form.UIForm;
import org.cyk.ui.api.model.table.Table;
import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractWindow<FORM,OUTPUTLABEL,INPUT,SELECTITEM> extends AbstractBean implements UIWindow<FORM,OUTPUTLABEL,INPUT,SELECTITEM>,Serializable {

	private static final long serialVersionUID = 7282005324574303823L;

	@Getter protected String title;
	
	@Override
	public UIForm<FORM,OUTPUTLABEL,INPUT,SELECTITEM> formInstance(Object anObjectModel) {
		UIForm<FORM,OUTPUTLABEL,INPUT,SELECTITEM> formContainer = formInstance();
		formContainer.setWindow(this);
		((AbstractBean)formContainer).postConstruct();
		formContainer.build(anObjectModel);
		return formContainer;
	}
	
	@Override
	public <DATA> Table<DATA> tableInstance(Class<DATA> aDataClass) {
		return new Table<>(aDataClass, this);
	}
	
}
