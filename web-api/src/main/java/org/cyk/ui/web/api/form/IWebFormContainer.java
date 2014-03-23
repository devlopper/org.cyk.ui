package org.cyk.ui.web.api.form;

import javax.faces.model.SelectItem;

import org.cyk.ui.api.form.UIFormContainer;
import org.cyk.ui.web.api.ObjectConverter;

public interface IWebFormContainer<FORM,OUTPUTLABEL,INPUT> extends UIFormContainer<FORM,OUTPUTLABEL,INPUT,SelectItem> {

	ObjectConverter getObjectConverter();
	
}
