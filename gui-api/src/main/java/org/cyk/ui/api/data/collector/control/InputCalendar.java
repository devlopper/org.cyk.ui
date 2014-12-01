package org.cyk.ui.api.data.collector.control;

import java.util.Date;

public interface InputCalendar<MODEL, ROW, LABEL, CONTROL, CHOICE_ITEM> extends Input<Date,MODEL, ROW, LABEL, CONTROL, CHOICE_ITEM> {

	
	String getPattern();
	void setPattern(String aPattern);
	
}
