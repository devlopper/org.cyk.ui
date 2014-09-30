package org.cyk.ui.web.api.data.collector.control;

import javax.faces.model.SelectItem;

import org.cyk.ui.api.data.collector.control.Control;
import org.cyk.ui.web.api.CascadeStyleSheet;

public interface WebControl<MODEL, ROW, LABEL, CONTROL> extends Control<MODEL, ROW, LABEL, CONTROL, SelectItem> {

	CascadeStyleSheet getCss();
	void setCss(CascadeStyleSheet aCascadeStyleSheet);
	
}
