package org.cyk.ui.api;

import java.util.Collection;

public interface View {

	String getTitle();
	void setTitle(String title);
	
	//TODO to be changed to template Id
	String getTemplate();
	void setTemplate(String template);
	
	Collection<View> getChildren();
	
	Collection<ViewListener> getViewListeners();

	void build();
}
