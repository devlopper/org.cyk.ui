package org.cyk.ui.api.layout;

import java.util.Collection;

import org.cyk.ui.api.component.IComponent;
import org.cyk.utility.common.cdi.provider.CommonMethodProvider;

/**
 * A layout is a way of disposition of visual component.
 * Generally , a layout can be represent as a grid where each
 * component occupy some cells in width and height and starting at
 * some top and left.
 * @author Komenan Y .Christian
 *
 */
public interface ILayout {
	
	Integer getColumnsCount();
	
	Collection<ILayoutRow> getRows();
	
	void createRow();
	
	void add(IComponent<?> component);
	
	CommonMethodProvider getCommonMethodProvider();
	
	/**
	 * The models to use to build the layout
	 */
	void model(Object...theObjects);
	
	void group(Class<?>...theGroupsClasses);
	
	void build();

}
