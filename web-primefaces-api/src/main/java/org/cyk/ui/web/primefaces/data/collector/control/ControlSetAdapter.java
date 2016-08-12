package org.cyk.ui.web.primefaces.data.collector.control;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.ui.api.data.collector.control.Control;
import org.cyk.ui.api.data.collector.control.Input;
import org.cyk.ui.api.data.collector.control.OutputLabel;
import org.cyk.ui.api.data.collector.form.ControlSet;
import org.cyk.ui.api.data.collector.form.ControlSetListener;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

public class ControlSetAdapter<DATA> implements ControlSetListener<DATA, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem>,Serializable {
	
	private static final long serialVersionUID = -4469671146996017509L;

	@Override
	public DynaFormModel createModel(ControlSet<DATA, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> controlSet) {
		return null;
	}

	@Override
	public Boolean canCreateRow(ControlSet<DATA, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> controlSet, Object object) {
		return null;
	}

	@Override
	public DynaFormRow createRow(ControlSet<DATA, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> controlSet) {
		return null;
	}

	@Override
	public DynaFormLabel createLabel(ControlSet<DATA, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> controlSet,
			OutputLabel<DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> outputLabel, DynaFormRow row) {
		return null;
	}

	@Override
	public DynaFormControl createControl(ControlSet<DATA, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> controlSet,
			Control<DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> control, DynaFormRow row) {
		return null;
	}

	@Override
	public void setControlLabel(ControlSet<DATA, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> controlSet, DynaFormControl control,
			DynaFormLabel label) {
		
	}

	@Override
	public void sort(List<Field> fields) {
		
	}

	@Override
	public Boolean build(Field field) {
		return null;
	}

	@Override
	public void labelBuilt(
			ControlSet<DATA, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> controlSet,
			Field field, DynaFormLabel label) {
		
	}
	
	@Override
	public String fiedLabel(
			ControlSet<DATA, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> controlSet,
			Field field) {
		return null;
	}

	@Override
	public void input(
			ControlSet<DATA, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> controlSet,
			Input<?, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> input) {
		
	}

	@Override
	public Boolean showFieldLabel(ControlSet<DATA, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> controlSet,Field field) {
		return null;
	}
	
	protected Boolean isFieldNameIn(Field field,String...names){
		return ArrayUtils.contains(names, field.getName());
	}
	
	protected Boolean isFieldNameNotIn(Field field,String...names){
		return Boolean.FALSE.equals(isFieldNameIn(field, names));
	}
	
	protected Boolean isBusinessIdentificationField(Field field){
		return isFieldNameIn(field, CODE,NAME);
	}
	

	/**/
	
	public static final String IMAGE = "image";
	public static final String CODE = "code";
	public static final String NAME = "name";
	public static final String ABBREVIATION = "abbreviation";
	public static final String DESCRIPTION = "description";
	public static final String EXISTENCE_PERIOD = "existencePeriod";
}
