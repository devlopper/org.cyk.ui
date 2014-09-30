package org.cyk.ui.api.data.collector.form;

import java.util.Stack;

import org.cyk.ui.api.View;

public interface Form<DATA,FORM,ROW,OUTPUTLABEL,INPUT,SELECTITEM> extends View {
	/*
	UIWindow<FORM,OUTPUTLABEL,INPUT,SELECTITEM,?> getWindow();
	void setWindow(UIWindow<FORM,OUTPUTLABEL,INPUT,SELECTITEM,?> aWindow);
	
	UIMenu getMenu();
	void setMenu(UIMenu aMenu);
	
	void setEditable(Boolean editable);
	Boolean getEditable();
	
	DATA getData();
	void setData(DATA data);
	*/
	
	FormData<DATA,FORM,ROW, OUTPUTLABEL, INPUT, SELECTITEM> createFormData();
	Stack<FormData<DATA, FORM, ROW, OUTPUTLABEL, INPUT, SELECTITEM>> getFormDatas();
	
	/*
	UICommandable getSubmitCommandable();
	UICommandable getSwitchCommandable();
	UICommandable getBackCommandable();
	UICommandable getResetCommandable();
	UICommandable getCloseCommandable();
	
	FormData<DATA,FORM,ROW,OUTPUTLABEL,INPUT,SELECTITEM> getSelectedFormData();
	
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
