package org.cyk.ui.api.form;

import org.cyk.ui.api.IViewContainer;

public interface IFormContainer<FORM,OUTPUTLABEL,INPUT,SELECTITEM> extends IViewContainer {
	
	IForm<FORM,OUTPUTLABEL,INPUT,SELECTITEM> getSelected();
	
	//void setSelected(IForm<FORM,OUTPUTLABEL,INPUT,SELECTITEM> selected);
	
	void switchTo(IForm<FORM,OUTPUTLABEL,INPUT,SELECTITEM> selected);
	
	void back();
	
	//Stack<IForm<FORM,OUTPUTLABEL,INPUT,SELECTITEM>> getStack();
	
}
