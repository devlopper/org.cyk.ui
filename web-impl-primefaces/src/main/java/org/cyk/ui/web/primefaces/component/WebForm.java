package org.cyk.ui.web.primefaces.component;

import org.cyk.ui.api.component.input.IInputComponent;
import org.cyk.ui.api.component.output.IOutputLabel;
import org.cyk.ui.web.api.AbstractWebForm;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

public class WebForm extends AbstractWebForm<DynaFormModel,DynaFormLabel,DynaFormControl> {

	private static final long serialVersionUID = -2915809915934469649L;

	private DynaFormRow currentRow;
	
	public WebForm() {}
	
	@Override
	public DynaFormModel createModel() {
		return new DynaFormModel();
	}
	
	@Override 
	public void createRow() {
		currentRow = model.createRegularRow();
	}
	
	@Override
	public DynaFormLabel createOutputLabel(IOutputLabel anOutputLabel) {
		return currentRow.addLabel(anOutputLabel.getValue());
	}

	@Override
	public DynaFormControl createInput(IInputComponent<?> anInputComponent) {
		return currentRow.addControl(anInputComponent, anInputComponent.getFamily());
	}

	@Override
	public void link(DynaFormLabel anOutputLabel, DynaFormControl anInput) {
		anOutputLabel.setForControl(anInput);
	}
			
}
