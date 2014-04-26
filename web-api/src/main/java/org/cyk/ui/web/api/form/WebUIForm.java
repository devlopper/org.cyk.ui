package org.cyk.ui.web.api.form;

import javax.faces.model.SelectItem;

import org.cyk.ui.api.form.UIForm;
import org.cyk.ui.web.api.ObjectConverter;

public interface WebUIForm<FORM,OUTPUTLABEL,INPUT> extends UIForm<FORM,OUTPUTLABEL,INPUT,SelectItem> {

	ObjectConverter getObjectConverter();
	
}
