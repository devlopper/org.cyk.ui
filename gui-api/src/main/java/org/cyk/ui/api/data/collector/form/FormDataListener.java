package org.cyk.ui.api.data.collector.form;

import java.lang.reflect.Field;
import java.util.Collection;

public interface FormDataListener<DATA,FORM,ROW,LABEL,CONTROL,SELECTITEM> {

	void fieldsDiscovered(FormData<DATA,FORM,ROW,LABEL,CONTROL,SELECTITEM> formData,Collection<Field> fields);
	
	//void componentsDiscovered(FormData<DATA,FORM,LABEL,CONTROL,SELECTITEM> formData,Collection<UIInputOutputComponent<?>> components);
	
	//void inputSetCreated(FormInputs<DATA,FORM,OUTPUTLABEL,INPUT,SELECTITEM> formInputs,AbstractInputSet<DATA, FORM, OUTPUTLABEL, INPUT, SELECTITEM> inputSet);
	
}
