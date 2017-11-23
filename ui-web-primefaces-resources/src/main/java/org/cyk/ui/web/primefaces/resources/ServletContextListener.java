package org.cyk.ui.web.primefaces.resources;

import java.io.Serializable;

import javax.servlet.ServletContextEvent;

public class ServletContextListener extends org.cyk.ui.web.api.resources.ServletContextListener implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		super.contextInitialized(servletContextEvent);
		inject(PrimefacesResourcesManager.class).initializeContext(servletContextEvent);
	}
	
}
