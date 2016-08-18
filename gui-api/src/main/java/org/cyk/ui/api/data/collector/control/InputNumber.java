package org.cyk.ui.api.data.collector.control;


public interface InputNumber<MODEL, ROW, LABEL, CONTROL, CHOICE_ITEM> extends Input<Number,MODEL, ROW, LABEL, CONTROL, CHOICE_ITEM> {

	Number getMinimum();
	void setMinimum(Number value);
	void setMaximumToInfinite();
	
	Number getMaximum();
	void setMaximum(Number value);
	void setMinimumToInfinite();
	
	Integer getDecimalPlaces();
	void setDecimalPlaces(Integer value);
	
	String getDecimalSeparator();
	void setDecimalSeparator(String value);
	
	String getSymbol();
	void setSymbol(String value);
	
	String getSymbolPosition();
	void setSymbolPosition(String value);
}
