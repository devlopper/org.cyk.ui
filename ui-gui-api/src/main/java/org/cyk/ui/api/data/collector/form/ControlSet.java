package org.cyk.ui.api.data.collector.form;

import java.lang.reflect.Field;
import java.util.Collection;

import org.cyk.ui.api.View;
import org.cyk.ui.api.data.collector.control.Control;
import org.cyk.ui.api.data.collector.control.Input;

//TODO i think DATA should be remove because ControlSet can contain fields from various Objects
public interface ControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> extends View {

	ControlSetDescriptor getDescriptor();
	
	FormData<DATA, MODEL, ROW,LABEL, CONTROL, SELECTITEM> getFormData();
	void setFormData(FormData<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> formData);
	
	Collection<Control<MODEL,ROW,LABEL,CONTROL,SELECTITEM>> getControls(); 
	
	<T> T findInputByFieldName(Class<T> aClass,String fieldName);
	<T> T findControlByIndex(Class<T> aClass,Integer index);
	Input<?, ?, ?, ?, ?, ?> findInputByFieldName(String fieldName);
	
	Input<?, ?, ?, ?, ?, ?> removeInputByFieldName(String fieldName);
	
	MODEL getModel();
	
	Boolean getShowInFrame();
	void setShowInFrame(Boolean value);
	
	Collection<ControlSetListener<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM>> getControlSetListeners();
	
	void applyValuesToFields() throws Exception;

	ControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> addField(Object object,Field field);

	ControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> addSeperator(String label);
	
	ControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> addText(String text);
	
	ControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> row(Object object);
	
}
