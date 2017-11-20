package org.cyk.ui.web.primefaces.resources;

import java.io.Serializable;

import org.cyk.utility.common.userinterface.container.Form;
import org.cyk.utility.common.userinterface.container.Form.Detail.Builder.Target;
import org.cyk.utility.common.userinterface.output.OutputText;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

public class FormBuilderBasedOnDynamicForm extends Form.Detail.Builder.Target.Adapter.Default<DynaFormModel, DynaFormControl,DynaFormRow, DynaFormLabel> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	public DynaFormRow createRow(DynaFormModel model) {
		return model.createRegularRow();
	}
	
	@Override
	public DynaFormControl addControl(DynaFormRow row, org.cyk.utility.common.userinterface.Control control,String type) {
		return row.addControl(control, type,control.getArea().getLength().getDistance().intValue()*2-1,control.getArea().getWidth().getDistance().intValue());
	}
	
	@Override
	public DynaFormLabel addLabel(DynaFormRow row, OutputText outputText) {
		return row.addLabel(outputText.getPropertiesMap().getValue().toString(),1,outputText.getArea().getWidth().getDistance()==null ? 1 : outputText.getArea().getWidth().getDistance().intValue());  
	}
	
	@Override
	public Target<DynaFormModel,DynaFormControl, DynaFormRow, DynaFormLabel> link(DynaFormControl control,DynaFormLabel label) {
		label.setForControl(control);  
		return this;
	}
	
	@Override
	public String getType(org.cyk.utility.common.userinterface.Control control) {
		return PrimefacesResourcesManager.getComponentTypeForDynaForm(control.getClass());
	}
	
}