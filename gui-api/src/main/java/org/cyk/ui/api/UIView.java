package org.cyk.ui.api;

import java.util.Collection;

import org.cyk.ui.api.component.UIComponent;
import org.cyk.ui.api.layout.UILayout;

/**
 * A view is something we see and can be materialized as a frame.
 * It is set of components. the arrangement follow a layout
 * @author Komenan Y .Christian
 *
 */
public interface UIView {
	
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
	UIContainer getContainer();
	
	/**
	 * Set the view container
	 * @param aContainer
	 */
	void setContainer(UIContainer aContainer);
	
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
	
	
}
