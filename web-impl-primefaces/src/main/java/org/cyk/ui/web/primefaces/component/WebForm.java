package org.cyk.ui.web.primefaces.component;

import java.util.Arrays;
import java.util.Collection;

import javax.faces.model.SelectItem;

import org.cyk.ui.api.component.input.IInputComponent;
import org.cyk.ui.api.component.output.IOutputLabel;
import org.cyk.ui.web.api.AbstractWebForm;
import org.cyk.ui.web.primefaces.controller.MyEntity.MyDetails;
import org.cyk.ui.web.primefaces.controller.MyEntity.MyDetails2;
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
	
	@Override
	public Collection<?> load(Class<?> aClass) {
		if(MyDetails.class.equals(aClass))
			return Arrays.asList(new MyDetails(),new MyDetails());
		if(MyDetails2.class.equals(aClass))
			return Arrays.asList(new MyDetails2(),new MyDetails2(),new MyDetails2());
		return null;
	}
	
	@Override
	public SelectItem item(Object object) {
		return new SelectItem(object,object.toString());
	}
	
	@Override
	public WebForm createChild(IInputComponent<?> anInput) {
		WebForm form = new WebForm();
		form.setParent(this);
		return form;
	}

}
