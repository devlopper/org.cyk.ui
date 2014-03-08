package org.cyk.ui.api.component.input;

import java.util.Collection;


public interface IInputSelectOne<VALUE_TYPE,SELECT_ITEM> extends IInputComponent<VALUE_TYPE> {


	Collection<SELECT_ITEM> getItems();
	
}