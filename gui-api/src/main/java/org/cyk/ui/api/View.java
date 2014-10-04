package org.cyk.ui.api;

import java.util.Collection;

public interface View {

	String getTitle();
	void setTitle(String title);
	
	String getTemplateId();
	void setTemplateId(String templateId);
	
	Collection<View> getChildren();
	
	Collection<ViewListener> getViewListeners();

	void build();
}
