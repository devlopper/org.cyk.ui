package org.cyk.ui.api.editor.input;

import java.util.Collection;

public interface UIInputSelect<VALUE_TYPE,SELECT_ITEM> extends UIInputComponent<VALUE_TYPE> {

	Collection<SELECT_ITEM> getItems();
	
	Boolean isBoolean();
	
	Boolean isEnum();
	
	Boolean getAddable();
	
	Boolean isSelectItemForeign();
	
	Boolean isSelectItemEditable();
	
}