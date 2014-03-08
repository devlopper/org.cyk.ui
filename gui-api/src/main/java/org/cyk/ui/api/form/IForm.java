package org.cyk.ui.api.form;

import java.util.Collection;

import org.cyk.ui.api.IView;
import org.cyk.ui.api.component.input.IInputComponent;
import org.cyk.ui.api.component.output.IOutputLabel;

public interface IForm<FORM,OUTPUTLABEL,INPUT,SELECTITEM> extends IView {
	
	/**
	 * The real component to use
	 * @param inputComponent
	 * @return
	 */
	IInputComponent<?> input(IInputComponent<?> anIInput);
	
	/**
	 * The parent form
	 * @return
	 */
	//IForm<FORM, OUTPUTLABEL, INPUT, SELECTITEM> getParent();
	
	//void setParent(IForm<FORM, OUTPUTLABEL, INPUT, SELECTITEM> form);
	
	/**
	 * Create a form model
	 * @return
	 */
	FORM createModel();
	
	/**
	 * The form model
	 * @return
	 */
	FORM getModel();
	
	/**
	 * Create a form for anInput
	 * @param anInput
	 * @return
	 */
	//IForm<FORM,OUTPUTLABEL,INPUT,SELECTITEM> createChild(IInputComponent<?> anInput);
	
	/**
	 * Create an input field label
	 * @param anOutputLabel
	 * @return
	 */
	OUTPUTLABEL createOutputLabel(IOutputLabel anOutputLabel);
	
	/**
	 * Create an input
	 * @param anInputComponent
	 * @return
	 */
	INPUT createInput(IInputComponent<?> anInputComponent);
	
	/**
	 * The fields of the form
	 * @return
	 */
	Collection<IInputComponent<?>> getFields();
	/*
	Collection<IFormCommand> getCommands();
	
	IFormCommand getSubmitCommand();
	*/
	/**
	 * The container of the form
	 * @return
	 */
	IFormContainer<FORM, OUTPUTLABEL, INPUT, SELECTITEM> getContainer();
	
	void setContainer(IFormContainer<FORM, OUTPUTLABEL, INPUT, SELECTITEM> container);
	
	void link(OUTPUTLABEL anOutputLabel,INPUT anInput);
	
	void updateFieldsValue() throws Exception;
	
	IInputComponent<?> getParentField();
	
	void setParentField(IInputComponent<?> aParentField);
	
}
