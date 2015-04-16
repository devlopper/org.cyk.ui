package org.cyk.ui.api.data.collector.form;

import org.cyk.ui.api.View;
import org.cyk.ui.api.command.UICommandable;

public interface Form<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM> extends View {
	
	String getFieldsRequiredMessage();
	void setFieldsRequiredMessage(String message);
	
	UICommandable getSubmitCommandable();
	
	Boolean getShowCommands();
	void setShowCommands(Boolean value);
	
	void setEditable(Boolean editable);
	Boolean getEditable();
	
	void setDynamic(Boolean value);
	Boolean getDynamic();
		
}
