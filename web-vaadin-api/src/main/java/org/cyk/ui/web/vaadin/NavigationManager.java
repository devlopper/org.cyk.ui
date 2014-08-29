package org.cyk.ui.web.vaadin;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

public class NavigationManager extends UI {
	
	private static final long serialVersionUID = -354787352325602050L;

	Navigator navigator;
    
    protected static final String MAINVIEW = "main";

    @Override
    protected void init(VaadinRequest request) {
        
        // Create a navigator to control the views
        navigator = new Navigator(this, this);
        
        // Create and register the views
        //navigator.addView("", new StartView());
        //navigator.addView(MAINVIEW, new MainView());
    }
}