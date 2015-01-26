package org.cyk.ui.api.data.collector.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.AbstractView;

public abstract class AbstractFormData<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> extends AbstractView implements FormData<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM>, 
	ControlSetListener<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM>,Serializable {

	private static final long serialVersionUID = 7441937769450315724L;

	@Getter @Setter protected FormOneData<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> form;
	@Getter @Setter protected DATA data;
	@Getter protected Collection<ControlSet<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM>> controlSets = new ArrayList<>();
	@Getter @Setter protected Boolean customControlSetPositioning=Boolean.FALSE;
	
	@Getter protected Collection<FormDataListener<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM>> formDataListeners = new ArrayList<>();
	
	@Override
	public void applyValuesToFields() throws Exception {
		for(ControlSet<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> set : controlSets)
			set.applyValuesToFields();
	}
	
	@Override
	public Boolean hasControlSetByIndex(Integer index) {
		return index < controlSets.size();
	}
	
	@Override
	public ControlSet<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> controlSetByIndex(Integer index) {
		return hasControlSetByIndex(index)? ((List<ControlSet<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM>>)controlSets).get(0):null;
	}
	
	@Override
	public <T> T findInputByFieldName(Class<T> aClass, String fieldName) {
		for(ControlSet<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> set : controlSets){
			T object = set.findInputByFieldName(aClass, fieldName);
			if(object!=null)
				return object;
		}
		return null;
	}
}