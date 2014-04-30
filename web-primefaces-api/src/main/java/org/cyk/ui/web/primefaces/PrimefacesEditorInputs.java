package org.cyk.ui.web.primefaces;

import org.cyk.ui.api.component.UIComponent;
import org.cyk.ui.api.editor.output.IOutputLabel;
import org.cyk.ui.web.api.form.AbstractWebEditorInputs;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

public class PrimefacesEditorInputs extends AbstractWebEditorInputs<DynaFormModel,DynaFormLabel,DynaFormControl> {

	private static final long serialVersionUID = -2915809915934469649L;

	private DynaFormRow currentRow;
	
	public PrimefacesEditorInputs() {}
	
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
