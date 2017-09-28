package org.cyk.ui.api.data.collector.form;

import java.util.Collection;
import java.util.List;
import java.util.Stack;

import org.cyk.ui.api.UIWindow;
import org.cyk.ui.api.data.collector.control.Input;
import org.cyk.ui.api.data.collector.control.InputCollection;
import org.cyk.ui.api.model.AbstractItemCollection;
import org.cyk.ui.api.model.FormOneDataCollection;

public interface FormOneData<DATA,FORM,ROW,OUTPUTLABEL,INPUT,SELECTITEM> extends Form<DATA,FORM,ROW,OUTPUTLABEL,INPUT,SELECTITEM> {
	
	UIWindow<FORM, OUTPUTLABEL, INPUT, SELECTITEM> getWindow();
	void setWindow(UIWindow<FORM, OUTPUTLABEL, INPUT, SELECTITEM> window);
	
	DATA getData();
	void setData(DATA data);
	
	FormData<DATA,FORM,ROW, OUTPUTLABEL, INPUT, SELECTITEM> createFormData();
	Stack<FormData<DATA, FORM, ROW, OUTPUTLABEL, INPUT, SELECTITEM>> getFormDatas();
	
	FormData<DATA,FORM,ROW,OUTPUTLABEL,INPUT,SELECTITEM> getSelectedFormData();
	
	<T> T findInputByClassByFieldName(Class<T> aClass,String fieldName);
	<T> T findControlByClassByIndex(Class<T> aClass, Integer index);
	Input<?, ?, ?, ?, ?, ?> getInputByFieldName(String fieldName);
	Input<?, ?, ?, ?, ?, ?> removeInputByFieldName(String fieldName);
	
	void setInputValue(String fieldName,Object value);
	void setDataFieldValue(String fromIdentifiableFieldName,String fromDataFieldName,String inputFieldName,Object object);
	void setDataFieldValue(String fromIdentifiableFieldName,String fromDataFieldName,Object object);
	void setDataFieldValue(String fromIdentifiableFieldName,Object object);
	
	void addChoices(String fieldName,List<SELECTITEM> choices);
	
	Collection<AbstractItemCollection<?,?,?,?>> getItemCollections();
	Collection<InputCollection<?, ?>> getInputCollections();
	
	FormOneDataCollection getCollection();
	void setCollection(FormOneDataCollection collection);
	
	void transfer();
	void validate();
	/*void serve();
	void succeed();
	void fail();
	void notifyAfterServe();
	void notificationMessageIdAfterServe();*/
	
}
