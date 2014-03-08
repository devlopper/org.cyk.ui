package org.cyk.ui.api.form;

import java.util.Collection;

import org.cyk.ui.api.IMessageManager;
import org.cyk.ui.api.IViewContainer;
import org.cyk.ui.api.component.input.IInputComponent;
import org.cyk.ui.api.component.input.IInputSelectOne;

/**
 * Contains forms
 * @author Komenan Y .Christian
 *
 * @param <FORM>
 * @param <OUTPUTLABEL>
 * @param <INPUT>
 * @param <SELECTITEM>
 */
public interface IFormContainer<FORM,OUTPUTLABEL,INPUT,SELECTITEM> extends IViewContainer {
	
	Object getObjectModel();
	
	/**
	 * The commands of the form container
	 * @return
	 */
	Collection<IFormCommand> getCommands();
	
	/**
	 * The submit command of the form container
	 * @return
	 */
	IFormCommand getSubmitCommand();
	
	/**
	 * The code to run on submit
	 * @param object
	 * @throws Exception 
	 */
	void onSubmit(Object object) throws Exception;
	
	/**
	 * The selected form of the form container
	 * @return
	 */
	IForm<FORM,OUTPUTLABEL,INPUT,SELECTITEM> getSelected();
	
	/**
	 * Switch to a given form
	 * @param selected
	 */
	void switchTo(IInputComponent<?> anInput);
	
	/**
	 * Go back to the previous form
	 */
	void back();
	
	/**
	 * The UI message manager
	 * @return
	 */
	IMessageManager getMessageManager();
	
	/**
	 * Dynamic load of data
	 * @param aClass
	 * @return
	 */
	<T> Collection<T> load(Class<T> aClass);
	
	/**
	 * Create item of input selection
	 * @param object
	 * @return
	 */
	SELECTITEM item(Object object);
	
	void addItem(IInputSelectOne<?,SELECTITEM> anInput,Object object);
	
	void updateValues() throws Exception;
	
	IForm<FORM, OUTPUTLABEL, INPUT, SELECTITEM> createForm();
	
	//void onAfterFormUpdated(IForm<FORM, OUTPUTLABEL, INPUT, SELECTITEM> aForm);
	
}
