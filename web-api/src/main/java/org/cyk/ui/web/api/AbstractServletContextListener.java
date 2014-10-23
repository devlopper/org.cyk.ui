package org.cyk.ui.web.api;

import java.io.Serializable;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.ui.api.MenuListener;
import org.cyk.ui.api.MenuManager;
import org.cyk.ui.api.UIManager;
import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractServletContextListener extends AbstractBean implements ServletContextListener,MenuListener,Serializable {

	private static final long serialVersionUID = 5382833444089348823L;
	
	@Inject protected UIManager uiManager;
	@Inject protected MenuManager menuManager;
	@Inject protected LanguageBusiness languageBusiness;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		menuManager.getMenuListeners().add(this);
	}
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		crudConfig(event);
	}
	
	protected void crudConfig(ServletContextEvent event){}
	
	/**/
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
	}
	
}
