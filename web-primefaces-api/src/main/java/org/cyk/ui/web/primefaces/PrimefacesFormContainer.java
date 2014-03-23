package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import javax.faces.model.SelectItem;

import lombok.Getter;

import org.cyk.ui.api.form.UIFormData;
import org.cyk.ui.web.api.form.AbstractWebFormContainer;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;

public class PrimefacesFormContainer extends AbstractWebFormContainer<DynaFormModel,DynaFormLabel,DynaFormControl> implements Serializable {

	private static final long serialVersionUID = -2915809915934469649L;
	
	@Getter private PrimefacesFormCommand primefacesSubmitCommand;
	@Getter private PrimefacesFormCommand primefacesBackCommand;
	@Getter private PrimefacesFormCommand primefacesSwitchCommand;
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		primefacesSubmitCommand = new PrimefacesFormCommand(submitCommand);
		primefacesBackCommand = new PrimefacesFormCommand(backCommand);
		primefacesSwitchCommand = new PrimefacesFormCommand(switchCommand);
	}
	
	@Override
	public UIFormData<DynaFormModel, DynaFormLabel, DynaFormControl, SelectItem> createFormData() {
		return new PrimefacesFormData();
	}

}
