package org.cyk.ui.api.data.collector.control;

import java.util.Date;

public interface InputCalendar<MODEL, ROW, LABEL, CONTROL, CHOICE_ITEM> extends Input<Date,MODEL, ROW, LABEL, CONTROL, CHOICE_ITEM> {

	String getPattern();
	void setPattern(String aPattern);
	
	Date getMinimum();
	void setMinimum(Date value);
	
	Date getMaximum();
	void setMaximum(Date value); 
	
}
