package org.cyk.ui.api;

import java.lang.reflect.Field;
import java.util.List;

import org.cyk.system.root.model.ContentType;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.data.collector.control.Control;
import org.cyk.ui.api.data.collector.control.InputChoice;

public interface UIProviderListener<MODEL,ROW,LABEL,CONTROL,SELECTITEM> {

	Class<? extends Control<?,?,?,?,?>> controlClassSelected(Class<? extends Control<?,?,?,?,?>> aClass);
	
	void controlInstanceCreated(Control<?,?,?,?,?> control);
	
	void choices(InputChoice<?,?,?,?,?,?> inputChoice,Object data,Field field,List<Object> list);
	
	Class<? extends UICommandable> commandableClassSelected(Class<? extends UICommandable> aClass);
	
	void commandableInstanceCreated(UICommandable aCommandable);
	
	String readOnlyValue(Field field,Object object);
	
	String formatValue(Field field,Object value);
	
	ContentType contentType();
	
}
