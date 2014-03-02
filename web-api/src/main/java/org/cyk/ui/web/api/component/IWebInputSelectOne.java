package org.cyk.ui.web.api.component;

import java.util.Collection;

import javax.faces.model.SelectItem;


public interface IWebInputSelectOne<VALUE_TYPE> extends IWebInputComponent<VALUE_TYPE> {

	Collection<SelectItem> getItems();
	
	void setItems(Collection<SelectItem> items);
	
}