package org.cyk.ui.web.primefaces.data.collector.control;

import java.lang.reflect.Field;
import java.util.List;

import javax.faces.model.SelectItem;

import org.cyk.ui.api.data.collector.control.Control;
import org.cyk.ui.api.data.collector.control.OutputLabel;
import org.cyk.ui.api.data.collector.form.ControlSet;
import org.cyk.ui.api.data.collector.form.ControlSetListener;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

public class ControlSetAdapter<DATA> implements ControlSetListener<DATA, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> {

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

}
