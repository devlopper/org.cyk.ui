package org.cyk.ui.api.form;

import java.util.Collection;

import org.cyk.ui.api.component.UIComponent;
import org.cyk.ui.api.component.output.UIOutputComponent;
import org.cyk.ui.api.form.input.UIInputComponent;
import org.cyk.ui.api.layout.UILayout;

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
public interface UISubForm<FORM,OUTPUTLABEL,INPUT,SELECTITEM>  {
	
	/**
	 * Get the layout of the view
	 * @return
	 */
	UILayout getLayout();

	/**
	 * Set the layout of the view
	 * @param aLayout
	 */
	void setLayout(UILayout aLayout);
	
	/**
	 * Get the view container
	 * @return
	 */
	UIForm<FORM,OUTPUTLABEL,INPUT,SELECTITEM> getContainer();
	
	/**
	 * Set the view container
	 * @param aContainer
	 */
	void setContainer(UIForm<FORM,OUTPUTLABEL,INPUT,SELECTITEM> aContainer);
	
	/**
	 * The title of the view
	 * @return
	 */
	String getTitle();

	/**
	 * The number of column of the view grid
	 * @return
	 */
	Integer getColumnsCount();
	
	/**
	 * The number of row of the view grid
	 * @return
	 */
	Integer getRowsCount();
	//Collection<ILayoutRow> getRows();
	
	/**
	 * Creates a row in the view grid
	 */
	void createRow();
	
	/**
	 * Adds a component to the current row in the view grid
	 * @param component
	 */
	void add(UIComponent<?> component);
	
	Collection<UIComponent<?>> getComponents();
	
	/**
	 * Set object model of the view
	 */
	void setObjectModel(Object anObjectModel);
	
	/**
	 * Get the object model of the view
	 * @return
	 */
	Object getObjectModel();
	
	/**
	 * The groups of components
	 * @param theGroupsClasses
	 */
	void group(Class<?>...theGroupsClasses);
	
	/**
	 * Builds the view
	 */
	void build();
	
	/**
	 * The real component to use
	 * @param inputComponent
	 * @return
	 */
	UIInputComponent<?> input(UIInputComponent<?> anIInput);
	
	UIOutputComponent<?> output(UIOutputComponent<?> anIOutput);
	
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
	 * The fields of the form
	 * @return
	 */
	Collection<UIInputComponent<?>> getInputFields();
	
	void link(OUTPUTLABEL anOutputLabel,INPUT anInput);
	
	void updateFieldsValue() throws Exception;
	
	UIInputComponent<?> getParentField();
	
	void setParentField(UIInputComponent<?> aParentField);
	
}
