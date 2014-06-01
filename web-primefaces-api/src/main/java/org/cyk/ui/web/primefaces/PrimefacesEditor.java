package org.cyk.ui.web.primefaces;

import java.beans.Introspector;
import java.io.Serializable;
import java.lang.reflect.Field;

import javax.faces.model.SelectItem;

import lombok.Getter;

import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.editor.EditorInputs;
import org.cyk.ui.web.api.form.AbstractWebEditor;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;

public class PrimefacesEditor extends AbstractWebEditor<DynaFormModel,DynaFormLabel,DynaFormControl> implements Serializable {

	private static final long serialVersionUID = -2915809915934469649L;
	
	@Getter private Command primefacesSubmitCommand;
	@Getter private Command primefacesBackCommand;
	@Getter private Command primefacesSwitchCommand;
	
	@Getter private MenuModel model = new DefaultMenuModel();
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		primefacesSubmitCommand =  new Command(submitCommand);
		primefacesBackCommand = new Command(backCommand);
		primefacesSwitchCommand = new Command(switchCommand);
	}
		
	@Override
	public void targetDependentInitialisation() {
		Field field = commonUtils.getField(getWindow(), this);
		for(UICommandable commandable : menu.getCommandables())
			model.addElement(CommandBuilder.getInstance().menuItem(commandable,null, Introspector.decapitalize(getWindow().getClass().getSimpleName()), field.getName(),"menu"));
	}
	
	@Override
	public EditorInputs<DynaFormModel, DynaFormLabel, DynaFormControl, SelectItem> createFormData() {
		return new PrimefacesEditorInputs();
	}

	

}
