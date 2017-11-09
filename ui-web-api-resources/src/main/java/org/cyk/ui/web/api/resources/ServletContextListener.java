package org.cyk.ui.web.api.resources;

import java.io.Serializable;

import javax.servlet.ServletContextEvent;

import org.cyk.utility.common.cdi.AbstractBean;

public class ServletContextListener extends AbstractBean implements javax.servlet.ServletContextListener , Serializable  {

	private static final long serialVersionUID = 1L;
	
	public static String CONTEXT;
	
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		CONTEXT = servletContextEvent.getServletContext().getContextPath();
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		
	}

}
