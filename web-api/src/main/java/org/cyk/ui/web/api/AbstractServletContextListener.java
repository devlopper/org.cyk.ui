package org.cyk.ui.web.api;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.BusinessManager;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.event.EventBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.message.MailBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.business.impl.RootRandomDataProvider;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.ContentType;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.Notification.RemoteEndPoint;
import org.cyk.ui.api.AbstractUserSession;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.UIProvider;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.menu.MenuListener;
import org.cyk.ui.api.command.menu.MenuManager;
import org.cyk.ui.api.command.menu.MenuManager.ModuleGroup;
import org.cyk.ui.api.command.menu.UIMenu;
import org.cyk.ui.api.config.IdentifiableConfiguration;
import org.cyk.ui.web.api.security.RoleManager;
import org.cyk.ui.web.api.security.shiro.Realm;
import org.cyk.ui.web.api.security.shiro.WebEnvironmentAdapter;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.cdi.AbstractBean;
import org.joda.time.DateTimeConstants;

public abstract class AbstractServletContextListener extends AbstractBean implements ServletContextListener,MenuListener,WebNavigationManagerListener,Serializable {

	private static final long serialVersionUID = 5382833444089348823L;
	
	@Inject protected GenericBusiness genericBusiness;
	@Inject protected EventBusiness eventBusiness;
	@Inject protected LanguageBusiness languageBusiness;
	@Inject protected MailBusiness mailBusiness;
	
	@Inject protected UIManager uiManager;
	@Inject protected UIProvider uiProvider;
	@Inject protected MenuManager menuManager;
	@Inject protected WebNavigationManager webNavigationManager;
	@Inject protected WebManager webManager;
	@Inject protected ApplicationBusiness applicationBusiness;
	@Inject protected BusinessManager businessManager;
	@Inject protected RoleManager roleManager;
	@Inject protected RootRandomDataProvider rootRandomDataProvider;
	
	
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
		UIManager.CONTENT_TYPE = ContentType.HTML;
		WebNavigationManager.init(event.getServletContext().getContextPath());
		mobileViewMapping();
		uiManager.businessEntityInfos(Event.class).setUiEditViewId(webNavigationManager.getOutcomeEventCrudOne());
		
		identifiableConfiguration(event);
		
		for(BusinessEntityInfos businessEntityInfos : applicationBusiness.findBusinessEntitiesInfos()){
			if(CrudStrategy.BUSINESS.equals(businessEntityInfos.getCrudStrategy())){
				@SuppressWarnings("unchecked")
				IdentifiableConfiguration configuration = uiManager.findConfiguration((Class<? extends AbstractIdentifiable>) businessEntityInfos.getClazz());
				if(configuration==null || configuration.getFormMap()==null){
					
				}else{
					if(StringUtils.isBlank(businessEntityInfos.getUiEditViewId()) && configuration.getFormMap().get(Boolean.TRUE, Crud.CREATE)!=null){
						businessEntityInfos.setUiEditViewId(webNavigationManager.getOutcomeDynamicCrudOne());
					}
					if(StringUtils.isBlank(businessEntityInfos.getUiListViewId()) && configuration.getFormMap().get(Boolean.FALSE, Crud.READ)!=null)
						businessEntityInfos.setUiListViewId(webNavigationManager.getOutcomeDynamicCrudMany());	
				}
				
			}
		}
		
		applicationBusiness.configureShiro();
		Realm.DATA_SOURCE = applicationBusiness.findShiroConfigurator().getDataSource();
		WebEnvironmentAdapter.DATA_SOURCE = Realm.DATA_SOURCE;
		
		if(Boolean.TRUE.equals(alarmScanningEnabled(event)))
			RootBusinessLayer.getInstance().enableAlarmScanning(alarmScanningDelay(event), alarmScanningPeriod(event),alarmScanningRemoteEndPoints(event));
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
	
	protected Boolean alarmScanningEnabled(ServletContextEvent event){
		return booleanContextParameter(ContextParam.ALARM_SCANNING_ENABLED,event,Boolean.FALSE);
	}
	
	protected Long alarmScanningDelay(ServletContextEvent event){
		return longContextParameter(ContextParam.ALARM_SCANNING_DELAY,event,DateTimeConstants.MILLIS_PER_MINUTE*1l);	
	}
	
	protected Long alarmScanningPeriod(ServletContextEvent event){
		return longContextParameter(ContextParam.ALARM_SCANNING_PERIOD,event,DateTimeConstants.MILLIS_PER_MINUTE*3l);
	}
	
	protected Set<RemoteEndPoint> alarmScanningRemoteEndPoints(ServletContextEvent event){
		Set<RemoteEndPoint> set = new LinkedHashSet<RemoteEndPoint>();
		if(booleanContextParameter(ContextParam.ALARM_SCANNING_REMOTEENDPOINTUIENABLED,event,Boolean.FALSE))
			set.add(RemoteEndPoint.USER_INTERFACE);
		if(booleanContextParameter(ContextParam.ALARM_SCANNING_REMOTEENDPOINTMAILENABLED,event,Boolean.FALSE))
			set.add(RemoteEndPoint.MAIL_SERVER);
		if(booleanContextParameter(ContextParam.ALARM_SCANNING_REMOTEENDPOINTSMSENABLED,event,Boolean.FALSE))
			set.add(RemoteEndPoint.PHONE);
		return set;
	}
	
	/**/
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		RootBusinessLayer.getInstance().disableAlarmScanning();
	}
	
	/**/
	
	protected String contextParameter(String name,ServletContextEvent event){
		return event.getServletContext().getInitParameter(name);
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T contextParameter(Class<T> aClass,String name,ServletContextEvent event,T defaultValue){
		String stringValue = contextParameter(name,event);
		if(stringValue==null)
			return defaultValue;
		Object value = null;
		try {
			if(String.class.equals(aClass))
				value = stringValue;
			else if(Boolean.class.equals(aClass))
				value = Boolean.parseBoolean(stringValue);
			else if(Long.class.equals(aClass))
				value = Long.parseLong(stringValue);
			else if(Integer.class.equals(aClass))
				value = Integer.parseInt(stringValue);
		} catch (Exception e) {
			return defaultValue;
		}
		__writeInfo__("Context parameter "+name+" set to "+value);
		return (T) value;
	}
	
	protected <T> T contextParameter(Class<T> aClass,String name,ServletContextEvent event){
		return contextParameter(aClass, name, event, null);
	}
	
	protected Boolean booleanContextParameter(String name,ServletContextEvent event,Boolean defaultValue){
		return Boolean.TRUE.equals(contextParameter(Boolean.class,name,event,defaultValue));
	}
	protected Boolean booleanContextParameter(String name,ServletContextEvent event){
		return booleanContextParameter(name, event, Boolean.FALSE);
	}
	
	protected String stringContextParameter(String name,ServletContextEvent event,String defaultValue){
		return contextParameter(String.class,name,event,defaultValue);
	}
	protected String stringContextParameter(String name,ServletContextEvent event){
		return stringContextParameter(name, event, null);
	}
	
	protected Long longContextParameter(String name,ServletContextEvent event,Long defaultValue){
		return contextParameter(Long.class,name,event,defaultValue);
	}
	protected Long longContextParameter(String name,ServletContextEvent event){
		return longContextParameter(name, event, 0l);
	}
	
	protected Integer integerContextParameter(String name,ServletContextEvent event,Integer defaultValue){
		return contextParameter(Integer.class,name,event,defaultValue);
	}
	protected Integer integerContextParameter(String name,ServletContextEvent event){
		return integerContextParameter(name, event, 0);
	}
	
}
