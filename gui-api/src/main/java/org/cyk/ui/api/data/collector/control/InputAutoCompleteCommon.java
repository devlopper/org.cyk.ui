package org.cyk.ui.api.data.collector.control;

import java.util.List;

import org.cyk.ui.api.CascadeStyleSheet;

public interface InputAutoCompleteCommon<VALUE_TYPE> {

	Integer getNumberOfCharacterBeforeQuery();
	void setNumberOfCharacterBeforeQuery(Integer value);
	
	Integer getNumberOfMillisecondBetweenQueries();
	void setNumberOfMillisecondBetweenQueries(Integer value);
	
	Boolean getForceSelectionEnabled();
	void setForceSelectionEnabled(Boolean value);
	
	Boolean getQueryResultsCacheEnabled();
	void setQueryResultsCacheEnabled(Boolean value);
	
	Integer getNumberOfMillisecondQueryResultsCacheTimeOut();
	void setNumberOfMillisecondQueryResultsCacheTimeOut(Integer value);
	
	String getNoResultMessage();
	void setNoResultMessage(String message);
	
	CascadeStyleSheet getResultsContainerCascadeStyleSheet();
	void setResultsContainerCascadeStyleSheet(CascadeStyleSheet cascadeStyleSheet);
	
	String getAppendTo();//TODO should be moved on subclass web
	void setAppendTo(String value);
	
	/**/
	
	List<VALUE_TYPE> complete(String query);
	
	String getLabel(VALUE_TYPE item);
	
	void onItemSelected(VALUE_TYPE item);
	
	void onItemUnSelected(VALUE_TYPE item);
	
	/**/
}
