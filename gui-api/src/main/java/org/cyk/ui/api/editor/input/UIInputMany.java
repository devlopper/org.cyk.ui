package org.cyk.ui.api.editor.input;

import org.cyk.ui.api.model.table.Table;

@SuppressWarnings("rawtypes")
public interface UIInputMany extends UIInputComponent<Table> {
	
	Table getTable();
	
	void setTable(Table aTable);

}