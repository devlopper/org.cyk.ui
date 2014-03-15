package org.cyk.ui.api.form;

import java.util.Collection;

import org.cyk.ui.api.UIView;
import org.cyk.ui.api.component.UIComponent;
import org.cyk.ui.api.component.output.UIOutputComponent;
import org.cyk.ui.api.form.input.UIInputComponent;

/**
 * A form data is a special kind of view.
 * It use input field to present data
 * @author Komenan Y .Christian
 *
 * @param <FORM>
 * @param <OUTPUTLABEL>
 * @param <INPUT>
 * @param <SELECTITEM>
 */
public interface UIFormData<FORM,OUTPUTLABEL,INPUT,SELECTITEM> extends UIView {
	
	/**
	 * The real component to use
	 * @param inputComponent
	 * @return
	 */
	UIInputComponent<?> input(UIInputComponent<?> anIInput);
	
	UIOutputComponent<?> output(UIOutputComponent<?> anIOutput);
	
	/**
	 * The parent form
	 * @return
	 */
	//IForm<FORM, OUTPUTLABEL, INPUT, SELECTITEM> getParent();
	
	//void setParent(IForm<FORM, OUTPUTLABEL, INPUT, SELECTITEM> form);
	
	/**
	 * Create a form data model
	 * @return
	 */
	FORM createDataModel();
	
	/**
	 * The form model
	 * @return
	 */
	FORM getDataModel();
	
	Object createComponent(UIComponent<?> aComponent);
	
	/**
	 * Create an input field label
	 * @param anOutputLabel
	 * @return
	 */
	//OUTPUTLABEL createOutputLabel(IOutputLabel anOutputLabel);
	
	/**
	 * Create an input
	 * @param anInputComponent
	 * @return
	 */
	//INPUT createInput(IInputComponent<?> anInputComponent);
	
	//void createOutputMessage(IOutputMessage anIOutputMessage);
	
	/**
	 * The fields of the form
	 * @return
	 */
	Collection<UIInputComponent<?>> getInputFields();
	
	/**
	 * The container of the form
	 * @return
	 */
	//IFormContainer<FORM, OUTPUTLABEL, INPUT, SELECTITEM> getContainer();
	
	//void setContainer(IFormContainer<FORM, OUTPUTLABEL, INPUT, SELECTITEM> container);
	
	void link(OUTPUTLABEL anOutputLabel,INPUT anInput);
	
	void updateFieldsValue() throws Exception;
	
	UIInputComponent<?> getParentField();
	
	void setParentField(UIInputComponent<?> aParentField);
	
}
