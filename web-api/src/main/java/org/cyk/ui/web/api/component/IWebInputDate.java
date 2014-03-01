package org.cyk.ui.web.api.component;

import java.util.Date;
import java.util.Locale;



public interface IWebInputDate extends IWebInputComponent<Date> {

	String getShowOn();
	
	Boolean getNavigator();
	
	Boolean getShowButtonPanel();
	
	Date getMinDate();
	
	Date getMaxDate();
	
	String getPattern();
	
	Locale getLocale();
	
	String getMode();
	
}