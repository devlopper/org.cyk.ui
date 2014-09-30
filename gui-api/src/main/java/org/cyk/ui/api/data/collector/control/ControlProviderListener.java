package org.cyk.ui.api.data.collector.control;

public interface ControlProviderListener<MODEL,ROW,LABEL,CONTROL,SELECTITEM> {

	Class<? extends Control<?,?,?,?,?>> controlClassSelected(Class<? extends Control<?,?,?,?,?>> aClass);
	
	void controlInstanceCreated(Control<?,?,?,?,?> control);
	
}
