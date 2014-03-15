package org.cyk.ui.web.api.form;

import org.cyk.ui.api.form.IFormContainer;
import org.cyk.ui.web.api.ObjectConverter;

public interface IWebFormContainer<FORM,OUTPUTLABEL,INPUT,SELECTITEM> extends IFormContainer<FORM,OUTPUTLABEL,INPUT,SELECTITEM> {

	ObjectConverter getObjectConverter();
	
}
