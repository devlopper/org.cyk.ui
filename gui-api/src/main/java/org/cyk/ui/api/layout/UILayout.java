package org.cyk.ui.api.layout;

import org.cyk.ui.api.component.UIInputOutputComponent;
import org.cyk.utility.common.AbstractMethod;


/**
 * A layout is a way of disposition of visual component.
 * It tells its container how it should arrange its components
 * @author Komenan Y .Christian
 *
 */
public interface UILayout {
	
	Integer getColumnsCount();
	
	void setColumnsCount(Integer aCount);
	
	Integer getRowsCount();
	
	void setRowsCount(Integer aCount);
	
	void addRow();
	
	void addColumn(UIInputOutputComponent<?>...components);
	
	void setOnAddRow(AbstractMethod<Object, Object> aMethod);
	
	AbstractMethod<Object, Object> getOnAddRow();

}
