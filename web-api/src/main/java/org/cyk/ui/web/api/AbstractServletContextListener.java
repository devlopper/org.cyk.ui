package org.cyk.ui.web.api;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.ServletContext;
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
import org.cyk.system.root.business.api.network.UniformResourceLocatorBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.api.security.RoleUniformResourceLocatorBusiness;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.business.impl.RootRandomDataProvider;
import org.cyk.system.root.business.impl.network.UniformResourceLocatorBuilder;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.ContentType;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.Notification.RemoteEndPoint;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.security.Role;
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
import org.cyk.ui.web.api.security.shiro.WebEnvironmentListener;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.cdi.AbstractBean;
import org.joda.time.DateTimeConstants;

public abstract class AbstractServletContextListener extends AbstractBean implements ServletContextListener,MenuListener,WebNavigationManager.Listener,Serializable {

	private static final long serialVersionUID = 5382833444089348823L;
	
	/*TODO to be moved on business with caching concept*/
	//public static final Map<String, Collection<UniformResourceLocator>> ROLE_UNIFORM_RESOURCE_LOCATOR_MAP = new HashMap<>();
	//public static final Map<Long, Collection<UniformResourceLocator>> USER_ACCOUNT_UNIFORM_RESOURCE_LOCATOR_MAP = new HashMap<>();
	
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
	protected RootBusinessLayer rootBusinessLayer;
	@Inject protected RootRandomDataProvider rootRandomDataProvider;
	@Inject protected UniformResourceLocatorBusiness uniformResourceLocatorBusiness;
	@Inject protected RoleUniformResourceLocatorBusiness roleUniformResourceLocatorBusiness;
	protected UniformResourceLocatorBuilder uniformResourceLocatorBuilder = new UniformResourceLocatorBuilder();
	
	protected ServletContext servletContext;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		@SuppressWarnings("unused")
		String g =  businessManager.findBusinessLayers().toString();//Needed to trigger eager deployment
		menuManager.getMenuListeners().add(this);
		WebNavigationManager.Listener.COLLECTION.add(this);	
		rootBusinessLayer = RootBusinessLayer.getInstance();
	}
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		servletContext = event.getServletContext();
		addUrls(event);
		UIManager.CONTENT_TYPE = ContentType.HTML;
		WebNavigationManager.init(event.getServletContext().getContextPath());
		mobileViewMapping();
		uiManager.businessEntityInfos(Event.class).getUserInterface().setEditViewId(webNavigationManager.getOutcomeEventCrudOne());
		
		identifiableConfiguration(event);
		
		for(BusinessEntityInfos businessEntityInfos : applicationBusiness.findBusinessEntitiesInfos()){
			if(CrudStrategy.BUSINESS.equals(businessEntityInfos.getCrudStrategy())){
				//uiManager.configBusinessIdentifiable(businessEntityInfos.getClazz(), null);
				@SuppressWarnings("unchecked")
				IdentifiableConfiguration configuration = uiManager.findConfiguration((Class<? extends AbstractIdentifiable>) businessEntityInfos.getClazz());
				if(configuration==null || configuration.getFormMap()==null){
					
				}else{
					if(StringUtils.isBlank(businessEntityInfos.getUserInterface().getEditViewId()) && configuration.getFormMap().get(Boolean.TRUE, Crud.CREATE)!=null)
						businessEntityInfos.getUserInterface().setEditViewId(webNavigationManager.getOutcomeDynamicCrudOne());
					
					if(StringUtils.isBlank(businessEntityInfos.getUserInterface().getConsultViewId()) && configuration.getFormMap().get(Boolean.TRUE, Crud.READ)!=null)
						businessEntityInfos.getUserInterface().setConsultViewId(webNavigationManager.getOutcomeDynamicCrudOne());
					
					if(StringUtils.isBlank(businessEntityInfos.getUserInterface().getListViewId()) && configuration.getFormMap().get(Boolean.FALSE, Crud.READ)!=null)
						businessEntityInfos.getUserInterface().setListViewId(webNavigationManager.getOutcomeDynamicCrudMany());	
					
					if(StringUtils.isBlank(businessEntityInfos.getUserInterface().getSelectOneViewId()) /*&& configuration.getFormMap().getQuery()!=null*/)
						businessEntityInfos.getUserInterface().setSelectOneViewId(webNavigationManager.getOutcomeDynamicSelectOne());
					
					if(StringUtils.isBlank(businessEntityInfos.getUserInterface().getSelectManyViewId()) /*&& configuration.getFormMap().getQuery()!=null*/)
						businessEntityInfos.getUserInterface().setSelectManyViewId(webNavigationManager.getOutcomeDynamicSelectMany());	
					
				}
				
			}
		}
		
		applicationBusiness.configureShiro();
		Realm.DATA_SOURCE = applicationBusiness.findShiroConfigurator().getDataSource();
		//WebEnvironmentAdapter.DATA_SOURCE = Realm.DATA_SOURCE;
		WebEnvironmentListener.Adapter.DATA_SOURCE = Realm.DATA_SOURCE;
		
		if(Boolean.TRUE.equals(alarmScanningEnabled(event)))
			RootBusinessLayer.getInstance().enableAlarmScanning(alarmScanningDelay(event), alarmScanningPeriod(event),alarmScanningRemoteEndPoints(event));
		
		
	}
	
	protected void addUrls(ServletContextEvent event){
		//TODO use shiro object instead for better management
		addUrl(Role.USER,"/private/index.jsf");
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
	public Boolean moduleGroupCreateable(AbstractUserSession userSession,ModuleGroup group) {return Boolean.TRUE;}
	
	@Override
	public void moduleGroupCreated(AbstractUserSession userSession, ModuleGroup group,UICommandable commandable) {}
	
	@Override
	public void referenceEntityMenuCreated(AbstractUserSession userSession, UIMenu menu) {}
	
	@Override
	public void referenceEntityGroupCreated(AbstractUserSession userSession,UICommandable referenceEntityGroup) {}
	
	@Override
	public void calendarMenuCreated(AbstractUserSession userSession, UIMenu menu) {
		
	}
	
	@Override
	public void sessionContextualMenuCreated(AbstractUserSession userSession,UIMenu menu) {
		
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
	
	protected void addUrl(String roleCode,UniformResourceLocatorBuilder builder){
		//Collection<UniformResourceLocator> uniformResourceLocators = ROLE_UNIFORM_RESOURCE_LOCATOR_MAP.get(roleCode);
		/*if(uniformResourceLocators==null)
			ROLE_UNIFORM_RESOURCE_LOCATOR_MAP.put(roleCode, uniformResourceLocators = new ArrayList<>());
		*/
		//uniformResourceLocators.add(builder.build());
	}
	
	//TODO this logic should be moved on root business
	protected void addUrl(String roleCode,String relativeUrl,Object...parameters){
		addUrl(roleCode, uniformResourceLocatorBuilder.newUniformResourceLocator()
			.setAddress(servletContext.getContextPath()+relativeUrl).addParameters(parameters).build());
	}
	
	protected void addUrl(String roleCode,UniformResourceLocator uniformResourceLocator){
		/*Collection<UniformResourceLocator> uniformResourceLocators = ROLE_UNIFORM_RESOURCE_LOCATOR_MAP.get(roleCode);
		if(uniformResourceLocators==null)
			ROLE_UNIFORM_RESOURCE_LOCATOR_MAP.put(roleCode, uniformResourceLocators = new ArrayList<>());
		
		uniformResourceLocators.add(uniformResourceLocator);*/
	}
	
	protected void addCrudUrl(String roleCode,Class<? extends AbstractIdentifiable> aClass,Boolean list,Crud...cruds){
		if(cruds==null)
			cruds = Crud.values();
		BusinessEntityInfos businessEntityInfos = uiManager.businessEntityInfos(aClass);
		String classIdentifier = businessEntityInfos.getIdentifier();
		if(Boolean.TRUE.equals(list)){
			addUrl(roleCode,WebNavigationManager.PAGE_CRUD_MANY,uiManager.getClassParameter(),classIdentifier);
		}
		for(Crud crud : cruds){
			switch(crud){
			case CREATE:
				addUrl(roleCode,WebNavigationManager.PAGE_CRUD_ONE,uiManager.getClassParameter(),aClass,uiManager.getCrudParameter()
						,uiManager.getCrudCreateParameter());
				break;
			case READ:
				addUrl(roleCode,WebNavigationManager.PAGE_CRUD_ONE,uiManager.getClassParameter(),aClass,uiManager.getCrudParameter()
						,uiManager.getCrudReadParameter());
				break;
			case UPDATE:
				addUrl(roleCode,WebNavigationManager.PAGE_CRUD_ONE,uiManager.getClassParameter(),aClass,uiManager.getCrudParameter()
						,uiManager.getCrudUpdateParameter());
				break;
			case DELETE:
				addUrl(roleCode,WebNavigationManager.PAGE_CRUD_ONE,uiManager.getClassParameter(),aClass,uiManager.getCrudParameter()
						,uiManager.getCrudDeleteParameter());
				break;
			}
		}
	}
	
	protected void addTableUrl(String roleCode,Class<? extends AbstractIdentifiable> aClass,String path,Boolean printable,Object...parameters){
		addUrl(roleCode,path,parameters);
		if(Boolean.TRUE.equals(printable)){
			addReportUrl(roleCode, aClass, Boolean.TRUE, parameters);
		}
	}
	protected void addTableUrl(String roleCode,Class<? extends AbstractIdentifiable> aClass,String path,Object...parameters){
		addTableUrl(roleCode, aClass, path, Boolean.TRUE, parameters);
	}
	
	protected void addReportUrl(String roleCode,Class<? extends AbstractIdentifiable> aClass,Boolean dynamic,Object...parameters){
		addUrl(roleCode,uniformResourceLocatorBuilder.newUniformResourceLocator().setAddress(servletContext.getContextPath()+"/private/__tools__/export/report.jsf")
				//.addAnyInstanceOf(aClass)
				.addClassParameter(aClass)
				.addParameters(parameters));
		if(Boolean.TRUE.equals(dynamic)){
			addUrl(roleCode,uniformResourceLocatorBuilder.newUniformResourceLocator().setAddress(servletContext.getContextPath()
					+"/private/__tools__/export/_cyk_report_/_dynamicbuilder_/_jasper_/").addClassParameter(aClass).addParameters(parameters));	
		}else{
			addUrl(roleCode,uniformResourceLocatorBuilder.newUniformResourceLocator().setAddress(servletContext.getContextPath()
					+"/private/__tools__/export/_cyk_report_/_business_/_jasper_/").addClassParameter(aClass).addParameters(parameters));	
		}
		
		
	}
	
	protected void addReportUrl(String roleCode,Class<? extends AbstractIdentifiable> aClass,Object...parameters){
		addReportUrl(roleCode, aClass, Boolean.TRUE, parameters);
	}
	
	//TODO to be moved to business
	/*
	public static Collection<UniformResourceLocator> getUrls(UserAccount userAccount){
		if(userAccount.getRoles().contains(RootBusinessLayer.getInstance().getManagerRole()))
			return null;
		Collection<UniformResourceLocator> uniformResourceLocators = USER_ACCOUNT_UNIFORM_RESOURCE_LOCATOR_MAP.get(userAccount.getIdentifier());
		if(uniformResourceLocators==null){
			uniformResourceLocators=new ArrayList<>();
			for(Role role : userAccount.getRoles()){
				for(Entry<String, Collection<UniformResourceLocator>> entry : ROLE_UNIFORM_RESOURCE_LOCATOR_MAP.entrySet()){
					if(entry.getKey().equals(role.getCode()) && entry.getValue()!=null){
						uniformResourceLocators.addAll(entry.getValue());
					}
				}
			}
		}else{
			
		}
		return uniformResourceLocators;
	}
	
	public static Collection<UniformResourceLocator> getUrls(){
		return ROLE_UNIFORM_RESOURCE_LOCATOR_MAP.get(RootBusinessLayer.getInstance().getUserRole().getCode());
	}
	*/

}
