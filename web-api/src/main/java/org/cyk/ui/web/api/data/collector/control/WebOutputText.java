package org.cyk.ui.web.api.data.collector.control;


public interface WebOutputText<MODEL, ROW, LABEL, CONTROL> extends WebOutput<MODEL, ROW, LABEL, CONTROL> {

	Boolean getEscape();
	void setEscape(Boolean aValue);
	
	
}
