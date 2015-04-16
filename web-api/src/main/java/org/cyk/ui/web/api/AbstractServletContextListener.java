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
import org.cyk.system.root.model.event.Notification.RemoteEndPoint;
import org.cyk.ui.api.AbstractUserSession;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.menu.MenuListener;
import org.cyk.ui.api.command.menu.MenuManager;
import org.cyk.ui.api.command.menu.MenuManager.ModuleGroup;
import org.cyk.ui.api.command.menu.UIMenu;
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
		
	}
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		webNavigationManager.setMobileContext(event.getServletContext().getContextPath()+WebNavigationManager.MOBILE_AGENT_FOLDER);
		WebNavigationManager.MOBILE_VIEW_MAP.put(event.getServletContext().getContextPath()+"/private/__tools__/event/agenda.jsf", 
			"/mobile/private/__tools__/event/list.jsf");
		identifiableConfiguration(event);
		applicationBusiness.configureShiro();
		Realm.DATA_SOURCE = applicationBusiness.findShiroConfigurator().getDataSource();
		WebEnvironmentAdapter.DATA_SOURCE = Realm.DATA_SOURCE;
		
		if(Boolean.TRUE.equals(alarmScanningEnabled()))
			RootBusinessLayer.getInstance().enableAlarmScanning(DateTimeConstants.MILLIS_PER_MINUTE*1l, alarmScanningPeriod(),alarmScanningRemoteEndPoints());
	}
	
	protected void identifiableConfiguration(ServletContextEvent event){}
	
	@Override
	public String homeUrl(AbstractUserSession userSession) {
		return null;
	}
	/*
	protected BusinessEntityInfos businessEntityInfos(Class<? extends AbstractIdentifiable> aClass){
		return uiManager.businessEntityInfos(aClass);
	}
	
	protected IdentifiableConfiguration identifiableConfiguration(Class<? extends AbstractIdentifiable> aClass){
		IdentifiableConfiguration identifiableConfiguration = uiManager.findConfiguration(aClass);
		if(identifiableConfiguration==null){
			identifiableConfiguration = new IdentifiableConfiguration();
			identifiableConfiguration.setIdentifiableClass(aClass);
			uiManager.registerConfiguration(identifiableConfiguration);
		}
		return identifiableConfiguration;
	}
	
	protected void registerFormModel(String id,Class<? extends AbstractFormModel<? extends AbstractIdentifiable>> aClass){
		UIManager.FORM_MODEL_MAP.put(id,aClass);
	}
	
	protected void registerFormModel(Class<? extends AbstractFormModel<? extends AbstractIdentifiable>> aClass){
		registerFormModel(aClass.getSimpleName(),aClass);//TODO ensure that there is no duplicate
	}
	*/
	/**/
	/*
	protected void businessClassConfig(Class<? extends AbstractIdentifiable> aClass,Class<? extends AbstractFormModel<? extends AbstractIdentifiable>> formModelClass,
			String uiEditViewId){
		identifiableConfiguration(aClass).setFormModelClass(formModelClass);
		BusinessEntityInfos businessEntityInfos = uiManager.businessEntityInfos(aClass);
		if(StringUtils.isEmpty(uiEditViewId))
			if(businessEntityInfos==null)
				;
			else
				uiEditViewId = businessEntityInfos.getUiEditViewId();
		businessEntityInfos(aClass).setUiEditViewId(uiEditViewId==null?webNavigationManager.getOutcomeDynamicCrudOne():uiEditViewId);
	}
	
	protected void businessClassConfig(Class<? extends AbstractIdentifiable> aClass,Class<? extends AbstractFormModel<? extends AbstractIdentifiable>> formModelClass){
		businessClassConfig(aClass,formModelClass,null);
	}
	*/
	/**/
	
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
		return Boolean.TRUE;
	}
	
	protected Long alarmScanningPeriod(){
		return DateTimeConstants.MILLIS_PER_MINUTE*1l;
	}
	
	protected Set<RemoteEndPoint> alarmScanningRemoteEndPoints(){
		return new LinkedHashSet<RemoteEndPoint>(Arrays.asList(RemoteEndPoint.USER_INTERFACE));
	}
	
	/**/
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		RootBusinessLayer.getInstance().disableAlarmScanning();
	}
	
}
