package org.cyk.ui.web.primefaces;

import org.cyk.ui.api.component.UIComponent;
import org.cyk.ui.api.form.output.IOutputLabel;
import org.cyk.ui.web.api.form.AbstractWebSubForm;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

public class PrimefacesSubForm extends AbstractWebSubForm<DynaFormModel,DynaFormLabel,DynaFormControl> {

	private static final long serialVersionUID = -2915809915934469649L;

	private DynaFormRow currentRow;
	
	public PrimefacesSubForm() {}
	
	@Override
	public DynaFormModel createDataModel() {
		return new DynaFormModel();
	}
	
	@Override 
	public void createRow() {
		currentRow = dataModel.createRegularRow();
	}
	
	@Override
	public Object createComponent(UIComponent<?> aComponent) {
		if(aComponent instanceof IOutputLabel)
			return currentRow.addLabel((String) aComponent.getValue());
		
		return currentRow.addControl(aComponent, aComponent.getFamily());
	}
	
	@Override
	public void link(DynaFormLabel anOutputLabel, DynaFormControl anInput) {
		anOutputLabel.setForControl(anInput);
	}

	@Override
	public String getInputTemplateFileAtRight() {
		return "template/form-inputMessageAtRight.xhtml";
	}

	@Override
	public String getInputTemplateFileAtTop() {
		return "template/form-inputMessageAtTop.xhtml";
	}
			
}
