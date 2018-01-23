package org.cyk.ui.web.primefaces.resources;

import java.io.Serializable;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;

public class ServletContextListener extends org.cyk.ui.web.api.resources.ServletContextListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject protected PrimefacesResourcesManager primefacesResourcesManager;
	
	@Override
	public void __contextInitialized__(ServletContextEvent servletContextEvent) {
		super.__contextInitialized__(servletContextEvent);
		primefacesResourcesManager.initializeContext(servletContextEvent);
	}
	
}
