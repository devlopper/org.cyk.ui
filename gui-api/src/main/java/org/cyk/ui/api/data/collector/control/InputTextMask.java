package org.cyk.ui.api.data.collector.control;


public interface InputTextMask<MODEL, ROW, LABEL, CONTROL, CHOICE_ITEM> extends Input<String,MODEL, ROW, LABEL, CONTROL, CHOICE_ITEM>{

	String getMask();
	void setMask(String mask);
	
}
