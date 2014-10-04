package org.cyk.ui.api.data.collector.form;

import java.lang.reflect.Field;
import java.util.Collection;

import org.cyk.ui.api.View;
import org.cyk.ui.api.data.collector.control.Control;

public interface ControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> extends View {

	ControlSetDescriptor getDescriptor();
	
	FormData<DATA, MODEL, ROW,LABEL, CONTROL, SELECTITEM> getFormData();
	void setFormData(FormData<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> formData);
	
	Collection<Control<MODEL,ROW,LABEL,CONTROL,SELECTITEM>> getControls(); 
	
	<T> T findInputByFieldName(Class<T> aClass,String fieldName);
	
	MODEL getModel();
	
	Boolean getShowInFrame();
	void setShowInFrame(Boolean value);
	
	Collection<ControlSetListener<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM>> getControlSetListeners();
	
	void applyValuesToFields() throws Exception;

	public abstract ControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> addField(Field field);

	public abstract ControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> row();
}
