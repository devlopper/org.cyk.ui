package org.cyk.ui.web.api;

import java.io.Serializable;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.cyk.system.root.business.api.BusinessManager;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.ui.api.MenuListener;
import org.cyk.ui.api.MenuManager;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.UserSession;
import org.cyk.ui.web.api.security.RoleManager;
import org.cyk.ui.web.api.security.shiro.Realm;
import org.cyk.ui.web.api.security.shiro.WebEnvironmentAdapter;
import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractServletContextListener extends AbstractBean implements ServletContextListener,MenuListener,WebNavigationManagerListener,Serializable {

	private static final long serialVersionUID = 5382833444089348823L;
	
	@Inject protected GenericBusiness genericBusiness;
	
	@Inject protected UIManager uiManager;
	@Inject protected MenuManager menuManager;
	@Inject protected LanguageBusiness languageBusiness;
	@Inject protected WebNavigationManager webNavigationManager;
	@Inject protected WebManager webManager;
	@Inject protected ApplicationBusiness applicationBusiness;
	@Inject protected BusinessManager businessManager;
	@Inject protected RoleManager roleManager;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		@SuppressWarnings("unused")
		String g =  businessManager.findBusinessLayers().toString();//Needed to trigger eager deployment
		menuManager.getMenuListeners().add(this);
		webNavigationManager.getWebNavigationManagerListeners().add(this);
	}
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		identifiableConfiguration(event);
		applicationBusiness.configureShiro();
		Realm.DATA_SOURCE = applicationBusiness.findShiroConfigurator().getDataSource();
		WebEnvironmentAdapter.DATA_SOURCE = Realm.DATA_SOURCE;
	}
	
	protected void identifiableConfiguration(ServletContextEvent event){}
	
	@Override
	public String homeUrl(UserSession userSession) {
		return null;
	}
	
	/**/
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
	}
	
}
