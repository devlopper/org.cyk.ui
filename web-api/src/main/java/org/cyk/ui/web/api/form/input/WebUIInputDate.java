package org.cyk.ui.web.api.form.input;

import java.util.Date;
import java.util.Locale;



public interface WebUIInputDate extends WebUIInputComponent<Date> {

	String getShowOn();
	
	Boolean getNavigator();
	
	Boolean getShowButtonPanel();
	
	Date getMinDate();
	
	Date getMaxDate();
	
	String getPattern();
	
	Locale getLocale();
	
	String getMode();
	
}