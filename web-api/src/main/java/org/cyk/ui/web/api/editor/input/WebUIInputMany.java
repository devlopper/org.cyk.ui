package org.cyk.ui.web.api.editor.input;

import org.cyk.ui.api.model.table.Table;

@SuppressWarnings("rawtypes")
public interface WebUIInputMany extends WebUIInputComponent<Table> {

	Table getTable();
	
	void setTable(Table aTable);
	
}