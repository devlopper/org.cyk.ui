package org.cyk.ui.api.form;

import java.util.Collection;

import org.cyk.ui.api.UIWindow;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UIMenu;
import org.cyk.ui.api.form.input.UIInputSelectOne;
import org.cyk.utility.common.AbstractMethod;

/**
 * A form. Content layout can be based on POJO.
 * @author Komenan Y .Christian
 *
 * @param <FORM>
 * @param <OUTPUTLABEL>
 * @param <INPUT>
 * @param <SELECTITEM>
 */
public interface UIForm<FORM,OUTPUTLABEL,INPUT,SELECTITEM> {
	
	UIWindow<FORM,OUTPUTLABEL,INPUT,SELECTITEM> getWindow();
	
	void setWindow(UIWindow<FORM,OUTPUTLABEL,INPUT,SELECTITEM> aWindow);
	
	UIMenu getMenu();
	
	void setMenu(UIMenu aMenu);
	
	/**
	 * Get the title
	 * @return
	 */
	String getTitle();
	
	Object getObjectModel();
	
	UISubForm<FORM,OUTPUTLABEL,INPUT,SELECTITEM> build(Object object);
	
	/**
	 * The submit command of the form container
	 * @return
	 */
	UICommand getSubmitCommand();
	
	UICommand getSwitchCommand();
	
	UICommand getBackCommand();
	
	UICommand getResetValuesCommand();
	
	UICommand getCloseCommand();
	
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
	UISubForm<FORM,OUTPUTLABEL,INPUT,SELECTITEM> getSelected();
	
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
	
	UISubForm<FORM, OUTPUTLABEL, INPUT, SELECTITEM> createFormData();
	
	//void onAfterFormUpdated(IForm<FORM, OUTPUTLABEL, INPUT, SELECTITEM> aForm);
	
	Boolean getRoot();
	
	
}
