package org.cyk.ui.api.data.collector.control;

import java.util.List;

public interface InputChoice<VALUE_TYPE,MODEL, ROW, LABEL, CONTROL, CHOICE_ITEM> extends Input<VALUE_TYPE,MODEL, ROW, LABEL, CONTROL, CHOICE_ITEM> {

	List<CHOICE_ITEM> getList();

	Boolean getFiltered();
	void setFiltered(Boolean filtered);
	
	FilterMode getFilterMode();
	void setFilterMode(FilterMode filterMode);
	
	void addChoice(Object object);
	void removeChoice(Object object);
	
	void removeSelected();
	
	Boolean getIsAutomaticallyRemoveSelected();
	void setIsAutomaticallyRemoveSelected(Boolean value);
	
	
	void addChoiceIfAutomaticallyRemoveSelected(Object object);
	void removeSelectedAutomatically();
	
	
	/**/
	
	public static enum FilterMode{STARTS_WITH,CONTAINS,ENDS_WITH}
	
}
