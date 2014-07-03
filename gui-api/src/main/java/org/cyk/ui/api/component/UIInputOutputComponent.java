package org.cyk.ui.api.component;

public interface UIInputOutputComponent<VALUE_TYPE> {
	
	String getId();
	
	String getName();
	
	String getFamily();

	Integer getLeftIndex();

	Integer getTopIndex();

	Integer getWidth();
	
	void setWidth(Integer aWidth);

	Integer getHeight();
	
	void setHeight(Integer aHeight);
	
	VALUE_TYPE getValue();
	
	void setValue(VALUE_TYPE value);

}
