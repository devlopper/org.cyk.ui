package org.cyk.ui.api.data.collector.form;

import java.util.Collection;
import java.util.List;
import java.util.Stack;

import org.cyk.ui.api.data.collector.control.Input;
import org.cyk.ui.api.model.AbstractItemCollection;

public interface FormOneData<DATA,FORM,ROW,OUTPUTLABEL,INPUT,SELECTITEM> extends Form<DATA,FORM,ROW,OUTPUTLABEL,INPUT,SELECTITEM> {
	
	DATA getData();
	void setData(DATA data);
	
	FormData<DATA,FORM,ROW, OUTPUTLABEL, INPUT, SELECTITEM> createFormData();
	Stack<FormData<DATA, FORM, ROW, OUTPUTLABEL, INPUT, SELECTITEM>> getFormDatas();
	
	FormData<DATA,FORM,ROW,OUTPUTLABEL,INPUT,SELECTITEM> getSelectedFormData();
	
	<T> T findInputByClassByFieldName(Class<T> aClass,String fieldName);
	<T> T findControlByClassByIndex(Class<T> aClass, Integer index);
	Input<?, ?, ?, ?, ?, ?> findInputByFieldName(String fieldName);
	
	void addChoices(String fieldName,List<SELECTITEM> choices);
	
	Collection<AbstractItemCollection<?,?>> getItemCollections();
	
}
