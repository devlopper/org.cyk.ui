package org.cyk.ui.api.editor.input;

import org.cyk.ui.api.model.table.AbstractTable;

@SuppressWarnings("rawtypes")
public interface UIInputMany extends UIInputComponent<AbstractTable> {
	
	AbstractTable<?,?,?> getTable();
	
	void setTable(AbstractTable<?,?,?> aTable);

}