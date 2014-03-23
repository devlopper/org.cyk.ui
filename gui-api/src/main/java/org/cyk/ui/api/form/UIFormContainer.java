package org.cyk.ui.api.form;

import java.util.Collection;

import org.cyk.ui.api.UIContainer;
import org.cyk.ui.api.form.input.UIInputSelectOne;
import org.cyk.utility.common.AbstractMethod;

/**
 * Contains forms
 * @author Komenan Y .Christian
 *
 * @param <FORM>
 * @param <OUTPUTLABEL>
 * @param <INPUT>
 * @param <SELECTITEM>
 */
public interface UIFormContainer<FORM,OUTPUTLABEL,INPUT,SELECTITEM> extends UIContainer<FORM,OUTPUTLABEL,INPUT,SELECTITEM> {
	
	Object getObjectModel();
	
	/**
	 * The commands of the form container
	 * @return
	 */
	Collection<UIFormCommand> getCommands();
	
	/**
	 * The submit command of the form container
	 * @return
	 */
	UIFormCommand getSubmitCommand();
	
	UIFormCommand getSwitchCommand();
	
	UIFormCommand getBackCommand();
	
	UIFormCommand getResetValuesCommand();
	
	UIFormCommand getCloseCommand();
	
	/**
	 * The code to run on submit
	 * @param object
	 * @throws Exception 
	 */
	void onSubmit(Object object) throws Exception;
	
	/**
	 * Go back to the previous form
	 */
	void onBack();
	
	/**
	 * Switch to a given form
	 * @param selected
	 */
	void onSwitch(Object object);
	
	void onResetValues();
	
	void onClose();
	
	void setSubmitMethodMain(AbstractMethod<Object, Object> anAbstractMethod);
	
	/**
	 * The selected form of the form container
	 * @return
	 */
	UIFormData<FORM,OUTPUTLABEL,INPUT,SELECTITEM> getSelected();
	
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
	
	void addItem(UIInputSelectOne<?,SELECTITEM> anInput,Object object);
	
	void updateValues() throws Exception;
	
	UIFormData<FORM, OUTPUTLABEL, INPUT, SELECTITEM> createFormData();
	
	//void onAfterFormUpdated(IForm<FORM, OUTPUTLABEL, INPUT, SELECTITEM> aForm);
	
	Boolean getRoot();
}
