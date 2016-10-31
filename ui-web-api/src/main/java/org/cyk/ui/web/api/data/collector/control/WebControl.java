package org.cyk.ui.web.api.data.collector.control;

import javax.faces.model.SelectItem;

import org.cyk.ui.api.data.collector.control.Control;

public interface WebControl<MODEL, ROW, LABEL, CONTROL> extends Control<MODEL, ROW, LABEL, CONTROL, SelectItem> {

	String getOnClick();
	void setOnClick(String script);
	
	String getWidgetVar();
	void setWidgetVar(String widgetVar);
	
}
