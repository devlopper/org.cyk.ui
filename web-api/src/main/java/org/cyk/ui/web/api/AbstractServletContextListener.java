package org.cyk.ui.web.api;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.cyk.system.root.business.api.BusinessManager;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.event.EventBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.Notification.RemoteEndPoint;
import org.cyk.ui.api.AbstractUserSession;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.menu.MenuListener;
import org.cyk.ui.api.command.menu.MenuManager;
import org.cyk.ui.api.command.menu.MenuManager.ModuleGroup;
import org.cyk.ui.api.command.menu.UIMenu;
import org.cyk.ui.api.model.ActorConsultFormModel;
import org.cyk.ui.web.api.security.RoleManager;
import org.cyk.ui.web.api.security.shiro.Realm;
import org.cyk.ui.web.api.security.shiro.WebEnvironmentAdapter;
import org.cyk.utility.common.cdi.AbstractBean;
import org.joda.time.DateTimeConstants;

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
	@Inject protected EventBusiness eventBusiness;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		@SuppressWarnings("unused")
		String g =  businessManager.findBusinessLayers().toString();//Needed to trigger eager deployment
		menuManager.getMenuListeners().add(this);
		webNavigationManager.getWebNavigationManagerListeners().add(this);
		
		uiManager.businessEntityInfos(Event.class).setUiEditViewId(webNavigationManager.getOutcomeEventCrudOne());
		
	}
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		//WebNavigationManager.CONTEXT_PATH = "";
		//webNavigationManager.setMobileContext(event.getServletContext().getContextPath()+WebNavigationManager.MOBILE_AGENT_FOLDER);
		WebNavigationManager.init(event.getServletContext().getContextPath());
		mobileViewMapping();
		/*
		WebNavigationManager.MOBILE_VIEW_MAP.put(event.getServletContext().getContextPath()+"/private/__tools__/event/agenda.jsf", 
			"/mobile/private/__tools__/event/list.jsf");
			*/
		uiManager.businessEntityInfos(Event.class).setUiEditViewId(webNavigationManager.getOutcomeEventCrudOne());
		UIManager.FORM_MODEL_MAP.put(uiManager.getFormModelActorParameter(), ActorConsultFormModel.class);
		identifiableConfiguration(event);
		applicationBusiness.configureShiro();
		Realm.DATA_SOURCE = applicationBusiness.findShiroConfigurator().getDataSource();
		WebEnvironmentAdapter.DATA_SOURCE = Realm.DATA_SOURCE;
		
		if(Boolean.TRUE.equals(alarmScanningEnabled()))
			RootBusinessLayer.getInstance().enableAlarmScanning(DateTimeConstants.MILLIS_PER_MINUTE*1l, alarmScanningPeriod(),alarmScanningRemoteEndPoints());
	}
	
	protected void mobileViewMapping(){
		webNavigationManager.mapMobileView("/private/__tools__/event/list", "/private/__tools__/event/agenda");
	}
	
	protected void identifiableConfiguration(ServletContextEvent event){}
	
	@Override
	public String homeUrl(AbstractUserSession userSession) {
		return null;
	}
	
	@Override
	public void applicationMenuCreated(AbstractUserSession userSession, UIMenu menu) {}
	
	@Override
	public void businessModuleGroupCreated(AbstractUserSession userSession,UICommandable commandableGroup) {}
	
	@Override
	public void moduleGroupCreated(AbstractUserSession userSession, ModuleGroup group,UICommandable commandable) {}
	
	@Override
	public void referenceEntityMenuCreated(AbstractUserSession userSession, UIMenu menu) {}
	
	@Override
	public void referenceEntityGroupCreated(AbstractUserSession userSession,UICommandable referenceEntityGroup) {}
	
	@Override
	public void calendarMenuCreated(AbstractUserSession userSession, UIMenu menu) {
		
	}
	
	/**/
	
	protected Boolean alarmScanningEnabled(){
		return Boolean.FALSE;
	}
	
	protected Long alarmScanningPeriod(){
		return DateTimeConstants.MILLIS_PER_MINUTE*1l;
	}
	
	protected Set<RemoteEndPoint> alarmScanningRemoteEndPoints(){
		return new LinkedHashSet<RemoteEndPoint>(Arrays.asList(RemoteEndPoint.USER_INTERFACE,RemoteEndPoint.MAIL_SERVER));
	}
	
	/**/
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		RootBusinessLayer.getInstance().disableAlarmScanning();
	}
	
}
