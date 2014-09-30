package org.cyk.ui.api.data.collector.form;

import org.cyk.ui.api.data.collector.control.Control;
import org.cyk.ui.api.data.collector.control.OutputLabel;


public interface ControlSetListener<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM> {

	MODEL createModel(ControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> controlSet);
	
	ROW createRow(ControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> controlSet);
	
	LABEL createLabel(ControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> controlSet,OutputLabel<MODEL,ROW, LABEL, CONTROL, SELECTITEM> outputLabel,ROW row);

	CONTROL createControl(ControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> controlSet,Control<MODEL,ROW,LABEL,CONTROL,SELECTITEM> control,ROW row);
	
	void setControlLabel(ControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> controlSet,CONTROL control,LABEL label);
	
	
	
}
