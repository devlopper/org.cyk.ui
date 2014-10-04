package org.cyk.ui.api.data.collector.control;


public interface InputTextarea<MODEL, ROW, LABEL, CONTROL, CHOICE_ITEM> extends Input<String,MODEL, ROW, LABEL, CONTROL, CHOICE_ITEM> {

	Integer getRows();
	void setRows(Integer rows);
	
	Integer getColumns();
	void setColumns(Integer columns);
	
}
