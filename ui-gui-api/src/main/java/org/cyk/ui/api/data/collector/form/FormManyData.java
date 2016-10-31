package org.cyk.ui.api.data.collector.form;

import java.util.Collection;
import java.util.List;

import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.model.table.AbstractTable;

public interface FormManyData<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM> extends Form<List<DATA>,MODEL,ROW,LABEL,CONTROL,SELECTITEM> {
	
	AbstractTable<DATA,?,?> getTable();

	Class<DATA> getDataType();
	
	UICommandable getAddCommandable();
	UICommandable getSaveCommandable();
	UICommandable getEditCommandable();
	UICommandable getCancelEditCommandable();
	UICommandable getDeleteCommandable();
	
	UICommandable getOpenCommandable();
	UICommandable getExportCommandable();
	
	<T> T findInputByFieldName(DATA data,Class<T> aClass,String fieldName);
	
	void addChoices(DATA data,String fieldName,List<SELECTITEM> choices);
	
	Collection<FormManyItemListener<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM>> getFormManyItemListeners();
	
}
