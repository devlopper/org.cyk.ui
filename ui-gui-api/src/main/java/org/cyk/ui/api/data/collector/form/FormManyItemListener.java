package org.cyk.ui.api.data.collector.form;

public interface FormManyItemListener<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM> {

	void itemAdded(DATA data);
	
	void itemEditing(DATA data);
	
	void itemSaved(DATA data);
	
	void itemRemoved(DATA data);
	
	void itemUpdated(DATA data);
	
}
