package org.cyk.ui.web.primefaces.data.collector.form;

import java.io.Serializable;

import javax.faces.model.SelectItem;

import org.cyk.ui.api.data.collector.control.Control;
import org.cyk.ui.api.data.collector.control.OutputLabel;
import org.cyk.ui.web.api.data.collector.form.AbstractWebFormData;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

public class FormData<DATA> extends AbstractWebFormData<DATA, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl> implements Serializable {

	private static final long serialVersionUID = -8473077267633574874L;

	@Override
	public ControlSet<DATA> createControlSet() {
		ControlSet<DATA> set = new org.cyk.ui.web.primefaces.data.collector.form.ControlSet<DATA>();
		set.setFormData(this);
		set.getControlSetListeners().add(this);
		
		controlSets.add(set);
		set.build();
		return set;
	}

	@Override
	public DynaFormModel createModel(org.cyk.ui.api.data.collector.form.ControlSet<DATA, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> controlSet) {
		return new DynaFormModel(); 
	}

	@Override
	public DynaFormRow createRow(org.cyk.ui.api.data.collector.form.ControlSet<DATA, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> controlSet) {
		return controlSet.getModel().createRegularRow();
	}

	@Override
	public DynaFormLabel createLabel(org.cyk.ui.api.data.collector.form.ControlSet<DATA, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> controlSet,
			OutputLabel<DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> outputLabel, DynaFormRow row) {
		return row.addLabel(outputLabel.getValue(), Boolean.TRUE.equals(((org.cyk.ui.web.primefaces.data.collector.control.OutputLabel)outputLabel).getEscape()), outputLabel.getPosition().getColumn().getSpan(), outputLabel.getPosition().getRow().getSpan());
	}

	@Override
	public DynaFormControl createControl(
			org.cyk.ui.api.data.collector.form.ControlSet<DATA, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> controlSet,
			Control<DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> control, DynaFormRow row) {
		return row.addControl(control,control.getType(), control.getPosition().getColumn().getSpan(), control.getPosition().getRow().getSpan());
	}

	@Override
	public void setControlLabel(org.cyk.ui.api.data.collector.form.ControlSet<DATA, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> controlSet,
			DynaFormControl control, DynaFormLabel label) {
		label.setForControl(control);
	}



}
