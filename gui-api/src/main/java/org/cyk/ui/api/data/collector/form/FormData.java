package org.cyk.ui.api.data.collector.form;

import java.util.Collection;

import org.cyk.ui.api.View;

public interface FormData<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM> extends View  {
		
	//Form<DATA,FORM,OUTPUTLABEL,INPUT,SELECTITEM> getForm();
	//void setForm(Form<DATA,FORM,OUTPUTLABEL,INPUT,SELECTITEM> form);
	
	DATA getData();
	void setData(DATA data);
	
	Collection<ControlSet<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM>> getControlSets();
	
	void applyValuesToFields() throws Exception;
	
	Collection<FormDataListener<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM>> getFormDataListeners();
	
	
	ControlSet<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> createControlSet();
	
}
