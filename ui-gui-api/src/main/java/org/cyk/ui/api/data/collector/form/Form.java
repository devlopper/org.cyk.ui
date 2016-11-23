package org.cyk.ui.api.data.collector.form;

import org.cyk.ui.api.AbstractUserSession;
import org.cyk.ui.api.View;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.menu.UIMenu;

public interface Form<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM> extends View {
	
	String getFieldsRequiredMessage();
	void setFieldsRequiredMessage(String message);
	
	UICommandable getSubmitCommandable();
	
	void setMenu(UIMenu menu);
	UIMenu getMenu();
	
	Boolean getShowCommands();
	void setShowCommands(Boolean value);
	
	void setEditable(Boolean editable);
	Boolean getEditable();
	
	void setDynamic(Boolean value);
	Boolean getDynamic();
	
	AbstractUserSession<?, ?> getUserSession();
	void setUserSession(AbstractUserSession<?, ?> userSession);
}
