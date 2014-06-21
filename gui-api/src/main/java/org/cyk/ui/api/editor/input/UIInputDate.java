package org.cyk.ui.api.editor.input;

import java.util.Date;

public interface UIInputDate extends UIInputComponent<Date> {
	
	String getPattern();
	
	void setPattern(String aPattern);

}