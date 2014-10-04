package org.cyk.ui.api.data.collector.form;

import java.util.List;
import java.util.Stack;

public interface FormOneData<DATA,FORM,ROW,OUTPUTLABEL,INPUT,SELECTITEM> extends Form<DATA,FORM,ROW,OUTPUTLABEL,INPUT,SELECTITEM> {
	/*
	UIWindow<FORM,OUTPUTLABEL,INPUT,SELECTITEM,?> getWindow();
	void setWindow(UIWindow<FORM,OUTPUTLABEL,INPUT,SELECTITEM,?> aWindow);
	
	UIMenu getMenu();
	void setMenu(UIMenu aMenu);
	
	*/
	
	DATA getData();
	void setData(DATA data);
	
	FormData<DATA,FORM,ROW, OUTPUTLABEL, INPUT, SELECTITEM> createFormData();
	Stack<FormData<DATA, FORM, ROW, OUTPUTLABEL, INPUT, SELECTITEM>> getFormDatas();
	
	FormData<DATA,FORM,ROW,OUTPUTLABEL,INPUT,SELECTITEM> getSelectedFormData();
	
	<T> T findInputByFieldName(Class<T> aClass,String fieldName);
	
	void addChoices(String fieldName,List<SELECTITEM> choices);
	
	/*
	
	UICommandable getSwitchCommandable();
	UICommandable getBackCommandable();
	UICommandable getResetCommandable();
	UICommandable getCloseCommandable();
	
	
	
	SELECTITEM item(Object object);
	
	
	void updateValues() throws Exception;
	
	
	
	Boolean getRoot();
	
	Boolean getShowCommands();
	void setShowCommands(Boolean aValue);
	
	Crud getCrud();
	void setCrud(Crud crud);
	
	Boolean getSubmitMethodMainExecuted();
	
	Collection<FormListener<FORM,OUTPUTLABEL,INPUT,SELECTITEM>> getFormListeners();
	*/
}
