package org.cyk.ui.api.data.collector.control;

import java.util.List;

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
	
	/**/
	
	List<VALUE_TYPE> complete(String query);
	
	void onItemSelected(VALUE_TYPE item);
	
	void onItemUnSelected(VALUE_TYPE item);
	
	/**/
}
