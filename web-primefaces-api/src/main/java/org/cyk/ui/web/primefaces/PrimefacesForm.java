package org.cyk.ui.web.primefaces;

import java.beans.Introspector;
import java.io.Serializable;

import javax.faces.model.SelectItem;

import lombok.Getter;

import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.form.UISubForm;
import org.cyk.ui.web.api.form.AbstractWebForm;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;

public class PrimefacesForm extends AbstractWebForm<DynaFormModel,DynaFormLabel,DynaFormControl> implements Serializable {

	private static final long serialVersionUID = -2915809915934469649L;
	
	private CommandBuilder commandBuilder = new CommandBuilder();
	
	@Getter private Command primefacesSubmitCommand;
	@Getter private Command primefacesBackCommand;
	@Getter private Command primefacesSwitchCommand;
	
	@Getter private MenuModel model = new DefaultMenuModel();
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		primefacesSubmitCommand = new Command(submitCommand);
		primefacesBackCommand = new Command(backCommand);
		primefacesSwitchCommand = new Command(switchCommand);
	}
	
	public void buldCommands(Class<?> managedBeanClass,String fieldName){
		for(UICommand command : menu.getCommands())
			model.addElement(commandBuilder.menuItem(command, Introspector.decapitalize(managedBeanClass.getSimpleName()), fieldName));
	}
	
	@Override
	public UISubForm<DynaFormModel, DynaFormLabel, DynaFormControl, SelectItem> createFormData() {
		return new PrimefacesSubForm();
	}

	

}
