package org.cyk.ui.api.form;

import java.util.Collection;

import org.cyk.ui.api.IView;
import org.cyk.ui.api.component.input.IInputComponent;
import org.cyk.ui.api.component.output.IOutputLabel;

public interface IForm<FORM,OUTPUTLABEL,INPUT,SELECTITEM> extends IView {
	
	IForm<FORM, OUTPUTLABEL, INPUT, SELECTITEM> getParent();
	
	void setParent(IForm<FORM, OUTPUTLABEL, INPUT, SELECTITEM> form);
	
	FORM createModel();
	
	FORM getModel();
	
	IForm<FORM,OUTPUTLABEL,INPUT,SELECTITEM> createChild(IInputComponent<?> anInput);
	
	OUTPUTLABEL createOutputLabel(IOutputLabel anOutputLabel);
	INPUT createInput(IInputComponent<?> anInputComponent);
	
	Collection<?> load(Class<?> aClass);
	
	SELECTITEM item(Object object);
	
	Collection<IFormField> getFields();
	
	Collection<IFormCommand> getCommands();
	
}
