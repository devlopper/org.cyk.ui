package org.cyk.ui.api.data.collector.control;


public interface InputOneCascadeList<VALUE_TYPE,MODEL, ROW, LABEL, CONTROL, CHOICE_ITEM> extends InputOneChoice<VALUE_TYPE,MODEL, ROW, LABEL, CONTROL, CHOICE_ITEM> {

	String getHeader();
	void setHeader(String header);
	
	Boolean getShowHeaders();
	void setShowHeaders(Boolean value);
}
