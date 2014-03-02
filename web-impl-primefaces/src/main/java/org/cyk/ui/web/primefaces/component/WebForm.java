package org.cyk.ui.web.primefaces.component;

import java.util.Arrays;
import java.util.Collection;

import javax.faces.model.SelectItem;

import lombok.Getter;

import org.cyk.ui.api.component.input.IInputComponent;
import org.cyk.ui.api.component.output.IOutputLabel;
import org.cyk.ui.web.api.AbstractWebForm;
import org.cyk.ui.web.primefaces.controller.MyEntity.MyDetails;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

public class WebForm extends AbstractWebForm<DynaFormLabel,DynaFormControl> {

	private static final long serialVersionUID = -2915809915934469649L;

	@Getter protected DynaFormModel model;
	private DynaFormRow currentRow;
		
	@Override
	protected void initialisation() {
		super.initialisation();
		model = new DynaFormModel();
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
	
	@Override
	public Collection<?> load(Class<?> aClass) {
		if(MyDetails.class.equals(aClass))
			return Arrays.asList(new MyDetails(),new MyDetails());
		return null;
	}
	
	@Override
	public SelectItem item(Object object) {
		return new SelectItem(object,object.toString());
	}

}
