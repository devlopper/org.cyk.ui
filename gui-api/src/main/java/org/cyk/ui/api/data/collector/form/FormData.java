package org.cyk.ui.api.data.collector.form;

import java.util.Collection;

import org.cyk.ui.api.View;

public interface FormData<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM> extends View  {
		
	FormOneData<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM> getForm();
	void setForm(FormOneData<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM> form);
	
	DATA getData();
	void setData(DATA data);
	
	Collection<ControlSet<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM>> getControlSets();
	
	void applyValuesToFields() throws Exception;
	
	Collection<FormDataListener<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM>> getFormDataListeners();
	
	ControlSet<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> createControlSet();
	
	Boolean getCustomControlSetPositioning();
	void setCustomControlSetPositioning(Boolean value);
	
	ControlSet<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> controlSetByIndex(Integer index);
	Boolean hasControlSetByIndex(Integer index);
	
	<T> T findInputByFieldName(Class<T> aClass,String fieldName);
	
}
