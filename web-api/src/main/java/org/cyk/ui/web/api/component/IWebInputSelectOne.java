package org.cyk.ui.web.api.component;

import java.util.Collection;

import javax.faces.model.SelectItem;

import org.cyk.ui.api.form.IForm;


public interface IWebInputSelectOne<VALUE_TYPE,FORM> extends IWebInputComponent<VALUE_TYPE> {

	Collection<SelectItem> getItems();
	
	void setItems(Collection<SelectItem> items);
	
	IForm<FORM, ?, ?, SelectItem> getForm();
	
	IForm<FORM, ?, ?, SelectItem> createForm();
	
}