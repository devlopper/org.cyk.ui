package org.cyk.ui.api.data.collector.form;

import java.lang.reflect.Field;

public interface FormOneDataListener<DATA,FORM,ROW,LABEL,CONTROL,SELECTITEM> {

	Boolean createRow(Field field);
	
}
