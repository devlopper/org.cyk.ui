package org.cyk.ui.api.component.input;

import java.util.Collection;


public interface IInputSelectOne<VALUE_TYPE> extends IInputComponent<VALUE_TYPE> {
	
	Collection<ISelectItem> getItems();
	
 

}