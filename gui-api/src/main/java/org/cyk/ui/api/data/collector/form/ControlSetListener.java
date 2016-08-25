package org.cyk.ui.api.data.collector.form;

import java.lang.reflect.Field;
import java.util.List;

import org.cyk.ui.api.data.collector.control.Control;
import org.cyk.ui.api.data.collector.control.Input;
import org.cyk.ui.api.data.collector.control.OutputLabel;


public interface ControlSetListener<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM> {

	void sort(List<Field> fields);
	Boolean build(Object data,Field field);
	String fiedLabel(ControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> controlSet,Object data,Field field);
	void labelBuilt(ControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> controlSet,Field field,LABEL label);
	void input(ControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> controlSet,Input<?, MODEL, ROW, LABEL, CONTROL, SELECTITEM> input);
	Boolean showFieldLabel(ControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> controlSet,Field field);
	
	MODEL createModel(ControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> controlSet);
	
	Boolean canCreateRow(ControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> controlSet,Object object);
	ROW createRow(ControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> controlSet);
	
	LABEL createLabel(ControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> controlSet,OutputLabel<MODEL,ROW, LABEL, CONTROL, SELECTITEM> outputLabel,ROW row);

	CONTROL createControl(ControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> controlSet,Control<MODEL,ROW,LABEL,CONTROL,SELECTITEM> control,ROW row);
	
	void setControlLabel(ControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> controlSet,CONTROL control,LABEL label);
	
	
	
}
