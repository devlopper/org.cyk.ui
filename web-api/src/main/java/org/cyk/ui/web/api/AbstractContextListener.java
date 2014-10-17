package org.cyk.ui.web.api;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.cyk.ui.api.UIManager;

public abstract class AbstractContextListener implements ServletContextListener {

	@Inject protected UIManager uiManager;
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub
		
	}
	
}
