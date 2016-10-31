package org.cyk.ui.api.data.collector.control;

import org.cyk.ui.api.CascadeStyleSheet;
import org.cyk.ui.api.UserDeviceType;
import org.cyk.ui.api.data.collector.form.AbstractControlSet;
import org.cyk.ui.api.data.collector.layout.Position;

public interface Control<MODEL,ROW,LABEL,CONTROL,CHOICE_ITEM> {

	AbstractControlSet<?,MODEL,ROW,LABEL,CONTROL,CHOICE_ITEM> getSet();
	void setSet(AbstractControlSet<?,MODEL,ROW,LABEL,CONTROL,CHOICE_ITEM> aSet);

	Position getPosition();
	void setPosition(Position aPosition); 

	String getType();
	void setType(String aType);
	
	String getId();
	void setId(String anId);
	
	CascadeStyleSheet getCss();
	void setCss(CascadeStyleSheet aCascadeStyleSheet);
	
	String getUniqueCssClass();
	void setUniqueCssClass(String value);
	
	UserDeviceType getUserDeviceType();
	
} 
