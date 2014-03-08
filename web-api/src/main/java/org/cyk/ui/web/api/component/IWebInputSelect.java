package org.cyk.ui.web.api.component;

import java.util.Collection;

import javax.faces.model.SelectItem;


public interface IWebInputSelect<VALUE_TYPE,FORM> extends IWebInputComponent<VALUE_TYPE> {

	Collection<SelectItem> getItems();
	
	void setItems(Collection<SelectItem> items);
	
	//IForm<FORM, ?, ?, SelectItem> getForm();
	
	//IForm<FORM, ?, ?, SelectItem> createForm();
	
}