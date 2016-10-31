package org.cyk.ui.api.data.collector.control;

public interface InputOneAutoComplete<VALUE_TYPE,MODEL, ROW, LABEL, CONTROL, CHOICE_ITEM,COMMON extends InputAutoCompleteCommon<VALUE_TYPE>> extends InputOneChoice<VALUE_TYPE,MODEL, ROW, LABEL, CONTROL, CHOICE_ITEM> {

	COMMON getCommon();
	void setCommon(COMMON common);
	
}
