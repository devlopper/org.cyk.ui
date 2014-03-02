package org.cyk.ui.api.form;

import java.util.Collection;

import org.cyk.ui.api.IView;
import org.cyk.ui.api.component.input.IInputComponent;
import org.cyk.ui.api.component.output.IOutputLabel;

public interface IForm<OUTPUTLABEL,INPUT,SELECTITEM> extends IView {
	
	OUTPUTLABEL createOutputLabel(IOutputLabel anOutputLabel);
	INPUT createInput(IInputComponent<?> anInputComponent);
	
	Collection<?> load(Class<?> aClass);
	
	SELECTITEM item(Object object);
	
	Collection<IFormField> getFields();
	
	Collection<IFormCommand> getCommands();
	
}
