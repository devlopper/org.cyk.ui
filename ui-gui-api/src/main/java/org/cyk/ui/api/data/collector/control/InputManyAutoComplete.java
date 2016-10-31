package org.cyk.ui.api.data.collector.control;


public interface InputManyAutoComplete<VALUE_TYPE,MODEL, ROW, LABEL, CONTROL, CHOICE_ITEM,COMMON extends InputAutoCompleteCommon<VALUE_TYPE>> extends InputManyChoice<VALUE_TYPE,MODEL, ROW, LABEL, CONTROL, CHOICE_ITEM>{	

	COMMON getCommon();
	void setCommon(COMMON common);
	
}
