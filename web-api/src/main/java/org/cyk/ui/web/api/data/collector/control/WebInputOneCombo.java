package org.cyk.ui.web.api.data.collector.control;


public interface WebInputOneCombo<MODEL, ROW, LABEL, CONTROL> extends WebInputOneChoice<MODEL, ROW, LABEL, CONTROL> {

	Boolean getFiltered();
	void setFiltered(Boolean filtered);
	
	String getFilterMode();
	void setFilterMode(String mode);
	
	/**/
	
	String FILTER_MODE_STARTS_WITH = "startsWith";
	String FILTER_MODE_CONTAINS = "contains";
	String FILTER_MODE_ENDS_WITH = "endsWith";
	
}
