package org.cyk.ui.api.component;

public interface IComponent<VALUE_TYPE> {
	
	String getId();
	
	String getName();
	
	String getFamily();

	Integer getLeftIndex();

	Integer getTopIndex();

	Integer getWidth();

	Integer getHeight();
	
	VALUE_TYPE getValue();

}
