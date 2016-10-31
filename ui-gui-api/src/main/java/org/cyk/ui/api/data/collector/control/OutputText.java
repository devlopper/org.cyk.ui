package org.cyk.ui.api.data.collector.control;


public interface OutputText<MODEL,ROW,LABEL,CONTROL,CHOICE_ITEM> extends Output<MODEL,ROW,LABEL,CONTROL,CHOICE_ITEM> {

	String getValue();
	void setValue(String aValue);
	
}
