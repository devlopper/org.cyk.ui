package org.cyk.ui.api.data.collector.control;


public interface InputManyCheckCombo<VALUE_TYPE,MODEL, ROW, LABEL, CONTROL, CHOICE_ITEM> extends InputManyChoice<VALUE_TYPE,MODEL, ROW, LABEL, CONTROL, CHOICE_ITEM> {

	String getPresentedLabel();
	void setPresentedLabel(String value);
}
