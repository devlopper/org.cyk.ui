package org.cyk.ui.api.data.collector.form;

import org.cyk.ui.api.View;
import org.cyk.ui.api.command.UICommandable;

public interface Form<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM> extends View {
	
	String getFieldsRequiredMessage();
	void setFieldsRequiredMessage(String message);
	
	UICommandable getSubmitCommandable();
	
	Boolean getShowCommands();
	void setShowCommands(Boolean value);
	
	/*
	UIWindow<FORM,OUTPUTLABEL,INPUT,SELECTITEM,?> getWindow();
	void setWindow(UIWindow<FORM,OUTPUTLABEL,INPUT,SELECTITEM,?> aWindow);
	
	UIMenu getMenu();
	void setMenu(UIMenu aMenu);
	*/
	void setEditable(Boolean editable);
	Boolean getEditable();
	
	void setDynamic(Boolean value);
	Boolean getDynamic();
		
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
