package org.cyk.ui.api.data.collector.form;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.AbstractView;
import org.cyk.ui.api.data.collector.control.Input;
import org.cyk.utility.common.AbstractFieldSorter.FieldSorter;
import org.cyk.utility.common.AbstractFieldSorter.ObjectField;
import org.cyk.utility.common.AbstractFieldSorter.ObjectFieldSorter;

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
	public <T> T findInputByClassByFieldName(Class<T> aClass, String fieldName) {
		for(ControlSet<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> set : controlSets){
			T object = set.findInputByFieldName(aClass, fieldName);
			if(object!=null)
				return object;
		}
		return null;
	}
	
	@Override
	public <T> T findControlByClassByIndex(Class<T> aClass, Integer index) {
		for(ControlSet<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> set : controlSets){
			T object = set.findControlByIndex(aClass, index);
			if(object!=null)
				return object;
		}
		return null;
	}
	
	@Override
	public Input<?, ?, ?, ?, ?, ?> findInputByFieldName(String fieldName) {
		for(ControlSet<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> set : controlSets){
			Input<?, ?, ?, ?, ?, ?> object = set.findInputByFieldName(fieldName);
			if(object!=null)
				return object;
		}
		return null;
	}
	
	@Override
	public Boolean build(Object data,Field field) {
		return null;
	}
	
	@Override
	public void labelBuilt(ControlSet<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> controlSet,Field field, LABEL label) {}
	
	@Override
	public String fiedLabel(ControlSet<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> controlSet,Object data,Field field) {
		return null;
	}
	
	@Override
	public void input(ControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> controlSet,Input<?, MODEL, ROW, LABEL, CONTROL, SELECTITEM> input) {}
	
	@Override
	public void sort(List<Field> fields) {}
	
	@Override
	public void sortObjectFields(List<ObjectField> objectFields, Class<?> aClass) {}
	
	@Override
	public FieldSorter getFieldSorter() {
		return null;
	}
	
	@Override
	public ObjectFieldSorter getObjectFieldSorter(List<ObjectField> objectFields, Class<?> aClass) {
		return null;
	}
	
	@Override
	public List<String> getExpectedFieldNames() {
		return null;
	}
	
	@Override
	public void setExpectedFieldNames(List<String> collection) {}
	
	@Override
	public String[] getFieldNames() {
		return null;
	}
	
	@Override
	public void setFieldNames(String[] names) {}
	
	@Override
	public String[] getRequiredFieldNames() {
		return null;
	}
	
	@Override
	public void setRequiredFieldNames(String[] names) {}
	
	@Override
	public Boolean showFieldLabel(ControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> controlSet,Field field) {
		return null;
	}
}
