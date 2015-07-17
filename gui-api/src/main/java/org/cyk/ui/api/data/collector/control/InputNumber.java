package org.cyk.ui.api.data.collector.control;


public interface InputNumber<MODEL, ROW, LABEL, CONTROL, CHOICE_ITEM> extends Input<Number,MODEL, ROW, LABEL, CONTROL, CHOICE_ITEM> {

	Number getMinimum();
	void setMinimum(Number value);
	
	Number getMaximum();
	void setMaximum(Number value);
}
