package org.cyk.ui.web.api;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.NavigationCase;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;

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
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.ContentType;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.Notification.RemoteEndPoint;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.UIProvider;
import org.cyk.ui.api.UniformResourceLocatorBuilderAdapter;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.menu.MenuManager;
import org.cyk.ui.api.command.menu.MenuManager.ModuleGroup;
import org.cyk.ui.api.command.menu.UIMenu;
import org.cyk.ui.api.config.IdentifiableConfiguration;
import org.cyk.ui.web.api.WebNavigationManager.Listener;
import org.cyk.ui.web.api.security.RoleManager;
import org.cyk.ui.web.api.security.shiro.Realm;
import org.cyk.ui.web.api.security.shiro.WebEnvironmentListener;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.FileExtension;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.builder.NameValueCollectionStringBuilder;
import org.cyk.utility.common.builder.TextStringBuilder;
import org.cyk.utility.common.builder.UrlStringBuilder;
import org.cyk.utility.common.cdi.AbstractBean;
import org.joda.time.DateTimeConstants;
import org.omnifaces.util.Faces;

public abstract class AbstractServletContextListener<NODE,NODE_MODEL extends WebHierarchyNode,USER_SESSION extends AbstractWebUserSession<NODE,NODE_MODEL>> extends AbstractBean implements ServletContextListener,MenuManager.Listener<USER_SESSION>,WebNavigationManager.Listener<USER_SESSION>,Serializable {

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
	@Inject protected UniformResourceLocatorBusiness uniformResourceLocatorBusiness;
	@Inject protected RoleUniformResourceLocatorBusiness roleUniformResourceLocatorBusiness;
	protected UniformResourceLocator.Builder uniformResourceLocatorBuilder = new UniformResourceLocator.Builder();
	
	protected ServletContext servletContext;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void initialisation() {
		super.initialisation();
		@SuppressWarnings("unused")
		String g =  businessManager.findBusinessLayers().toString();//Needed to trigger eager deployment
		menuManager.addMenuListener((org.cyk.ui.api.command.menu.MenuManager.Listener) this);
		WebNavigationManager.Listener.COLLECTION.add((Listener<AbstractWebUserSession<?,?>>) this);	
		rootBusinessLayer = RootBusinessLayer.getInstance();
		
		UrlStringBuilder.Listener.COLLECTION.add(new UrlStringBuilder.Listener.Adapter.Default(){
			private static final long serialVersionUID = 1L;
			
			@Override
			public HttpServletRequest getRequest() {
				return Faces.getRequest();
			}
			
			@Override
			public String getScheme(Object request) {
				return ((HttpServletRequest)request).getScheme();
			}
			
			@Override
			public String getHost(Object request) {
				return ((HttpServletRequest)request).getServerName();
			}
			
			@Override
			public Integer getPort(Object request) {
				return ((HttpServletRequest)request).getServerPort();
			}
			
		});
		
		UrlStringBuilder.PathStringBuilder.PATH_NOT_FOUND_IDENTIFIER = "oc";
		UrlStringBuilder.SCHEME = "http";
		UrlStringBuilder.PathStringBuilder.Listener.COLLECTION.add(new UrlStringBuilder.PathStringBuilder.Listener.Adapter.Default(){
			private static final long serialVersionUID = 1L;
			
			@Override
			public String getIdentifierMapping(String identifier) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				NavigationCase navigationCase = ((ConfigurableNavigationHandler)facesContext.getApplication().getNavigationHandler())
						.getNavigationCase(facesContext, null, identifier);			
				if(navigationCase==null){
					
				}else
					return navigationCase.getToViewId(facesContext);
				return super.getIdentifierMapping(identifier);
			}
			
			@Override
			public Map<String, String> getTokenReplacementMap() {
				Map<String, String> map = new HashMap<>();
				map.put(WebNavigationManager.FILE_STATIC_EXTENSION, WebNavigationManager.FILE_PROCESSING_EXTENSION);
				return map;
			}
			
			@Override
			public String getContext(Object request) {
				return ((HttpServletRequest)request).getSession().getServletContext().getContextPath();
			}
			
			@Override
			public String getConsultFilesIdentifier() {
				return WebNavigationManager.getInstance().getOutcomeFileConsultMany();
			}
		});
		
		UrlStringBuilder.QueryStringBuilder.Listener.COLLECTION.add(new UrlStringBuilder.QueryStringBuilder.Listener.Adapter.Default(){
			private static final long serialVersionUID = 1L;
			
			
		});
		
		NameValueCollectionStringBuilder.Listener.COLLECTION.add(new NameValueCollectionStringBuilder.Listener.Adapter.Default(){
			private static final long serialVersionUID = 1L;
			
			@Override
			public String getEncodedParameterName() {
				return RootConstant.Code.UniformResourceLocatorParameter.ENCODED;
			}
			
			@Override
			public String getFileExtensionName() {
				return UniformResourceLocatorParameter.FILE_EXTENSION;
			}
			
			@Override
			public FileExtension getFileExtension(Collection<?> files) {
				return FileExtension.PDF;
			}
			
			@Override
			public String getWindowsModeName() {
				return UniformResourceLocatorParameter.WINDOW_MODE;
			}
			
			@Override
			public String getWindowsModeDialogName() {
				return UniformResourceLocatorParameter.WINDOW_MODE_DIALOG;
			}
			
			@Override
			public String getIdentifiableName() {
				return UniformResourceLocatorParameter.IDENTIFIABLE;
			}
		});
	
		TextStringBuilder.Listener.COLLECTION.add(new TextStringBuilder.Listener.Adapter.Default(){
			private static final long serialVersionUID = 1L;
			
			@Override
			public String getIdentifierMapping(String identifier) {
				return inject(LanguageBusiness.class).findText(identifier);
			}
			
		});
	}
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		servletContext = event.getServletContext();
		UrlStringBuilder.PathStringBuilder.CONTEXT = StringUtils.replace(servletContext.getContextPath(),Constant.CHARACTER_SLASH.toString(),Constant.EMPTY_STRING);
		addUrls(event);
		UIManager.CONTENT_TYPE = ContentType.HTML;
		WebNavigationManager.init(event.getServletContext().getContextPath());
		mobileViewMapping();
		uiManager.businessEntityInfos(Event.class).getUserInterface().setEditViewId(webNavigationManager.getOutcomeEventCrudOne());
		UniformResourceLocator.Builder.Listener.COLLECTION.add(new UniformResourceLocatorBuilderAdapter());
		identifiableConfiguration(event);
		
		for(BusinessEntityInfos businessEntityInfos : applicationBusiness.findBusinessEntitiesInfos()){
			if(/*CrudStrategy.ENUMERATION.equals(businessEntityInfos.getCrudStrategy()) ||*/ CrudStrategy.BUSINESS.equals(businessEntityInfos.getCrudStrategy())){
				//uiManager.configBusinessIdentifiable(businessEntityInfos.getClazz(), null);
				@SuppressWarnings("unchecked")
				IdentifiableConfiguration configuration = uiManager.findConfiguration((Class<? extends AbstractIdentifiable>) businessEntityInfos.getClazz(),Boolean.FALSE);
				Boolean notConfigured = configuration==null || configuration.getFormMap()==null;
				
				if(notConfigured || StringUtils.isBlank(businessEntityInfos.getUserInterface().getEditViewId()) && configuration.getFormMap().get(Boolean.TRUE, Crud.CREATE)!=null)
					businessEntityInfos.getUserInterface().setEditViewId(webNavigationManager.getOutcomeDynamicCrudOne());
				
				if(notConfigured || StringUtils.isBlank(businessEntityInfos.getUserInterface().getConsultViewId()) && configuration.getFormMap().get(Boolean.TRUE, Crud.READ)!=null)
					businessEntityInfos.getUserInterface().setConsultViewId(webNavigationManager.getOutcomeDynamicCrudOne());
				
				if(notConfigured || StringUtils.isBlank(businessEntityInfos.getUserInterface().getListViewId()) && configuration.getFormMap().get(Boolean.FALSE, Crud.READ)!=null)
					businessEntityInfos.getUserInterface().setListViewId(webNavigationManager.getOutcomeDynamicCrudMany());	
				
				if(notConfigured || StringUtils.isBlank(businessEntityInfos.getUserInterface().getSelectOneViewId()) /*&& configuration.getFormMap().getQuery()!=null*/)
					businessEntityInfos.getUserInterface().setSelectOneViewId(webNavigationManager.getOutcomeDynamicSelectOne());
				
				if(notConfigured || StringUtils.isBlank(businessEntityInfos.getUserInterface().getSelectManyViewId()) /*&& configuration.getFormMap().getQuery()!=null*/)
					businessEntityInfos.getUserInterface().setSelectManyViewId(webNavigationManager.getOutcomeDynamicSelectMany());	
			}
		}
		
		webNavigationManager.useDynamicSelectView(Person.class);
		
		applicationBusiness.configureShiro();
		Realm.DATA_SOURCE = applicationBusiness.findShiroConfigurator().getDataSource();
		//WebEnvironmentAdapter.DATA_SOURCE = Realm.DATA_SOURCE;
		WebEnvironmentListener.Adapter.DATA_SOURCE = Realm.DATA_SOURCE;
		
		if(Boolean.TRUE.equals(alarmScanningEnabled(event)))
			RootBusinessLayer.getInstance().enableAlarmScanning(alarmScanningDelay(event), alarmScanningPeriod(event),alarmScanningRemoteEndPoints(event));
		
		
	}
	
	protected void addUrls(ServletContextEvent event){
		//TODO use shiro object instead for better management
		addUrl(RootConstant.Code.Role.USER,"/private/index.jsf");
	}
	
	protected void mobileViewMapping(){
		webNavigationManager.mapMobileView("/private/__dynamic__/event/list", "/private/__dynamic__/event/agenda");
	}
		
	protected void identifiableConfiguration(ServletContextEvent event){}
	
	@Override
	public String homeUrl(USER_SESSION userSession) {
		return null;
	}
	
	@Override
	public void applicationMenuCreated(USER_SESSION userSession, UIMenu menu) {}
	
	@Override
	public void businessModuleGroupCreated(USER_SESSION userSession,UICommandable commandableGroup) {}
	
	@Override
	public Boolean moduleGroupCreateable(USER_SESSION userSession,ModuleGroup group) {return Boolean.TRUE;}
	
	@Override
	public void moduleGroupCreated(USER_SESSION userSession, ModuleGroup group,UICommandable commandable) {}
	
	@Override
	public void referenceEntityMenuCreated(USER_SESSION userSession, UIMenu menu) {}
	
	@Override
	public void referenceEntityGroupCreated(USER_SESSION userSession,UICommandable referenceEntityGroup) {}
	
	@Override
	public void calendarMenuCreated(USER_SESSION userSession, UIMenu menu) {
		
	}
	
	@Override
	public void sessionContextualMenuCreated(USER_SESSION userSession,UIMenu menu) {
		
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
	
	protected void addUrl(String roleCode,UniformResourceLocator.Builder builder){
		//Collection<UniformResourceLocator> uniformResourceLocators = ROLE_UNIFORM_RESOURCE_LOCATOR_MAP.get(roleCode);
		/*if(uniformResourceLocators==null)
			ROLE_UNIFORM_RESOURCE_LOCATOR_MAP.put(roleCode, uniformResourceLocators = new ArrayList<>());
		*/
		//uniformResourceLocators.add(builder.build());
	}
	
	//TODO this logic should be moved on root business
	protected void addUrl(String roleCode,String relativeUrl,Object...parameters){
		addUrl(roleCode, UniformResourceLocator.Builder.create(servletContext.getContextPath()+relativeUrl,parameters));
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
			addUrl(roleCode,UniformResourceLocator.DYNAMIC_CRUD_MANY,UniformResourceLocatorParameter.CLASS,classIdentifier);
		}
		for(Crud crud : cruds){
			switch(crud){ 
			case CREATE:
				addUrl(roleCode,UniformResourceLocator.DYNAMIC_CRUD_ONE,UniformResourceLocatorParameter.CLASS,aClass,UniformResourceLocatorParameter.CRUD
						,UniformResourceLocatorParameter.CRUD_CREATE);
				break;
			case READ:
				addUrl(roleCode,UniformResourceLocator.DYNAMIC_CRUD_ONE,UniformResourceLocatorParameter.CLASS,aClass,UniformResourceLocatorParameter.CRUD
						,UniformResourceLocatorParameter.CRUD_READ);
				break;
			case UPDATE:
				addUrl(roleCode,UniformResourceLocator.DYNAMIC_CRUD_ONE,UniformResourceLocatorParameter.CLASS,aClass,UniformResourceLocatorParameter.CRUD
						,UniformResourceLocatorParameter.CRUD_UPDATE);
				break;
			case DELETE:
				addUrl(roleCode,UniformResourceLocator.DYNAMIC_CRUD_ONE,UniformResourceLocatorParameter.CLASS,aClass,UniformResourceLocatorParameter.CRUD
						,UniformResourceLocatorParameter.CRUD_DELETE);
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
		addUrl(roleCode,UniformResourceLocator.Builder.instanciateOne().setAddress(servletContext.getContextPath()+UniformResourceLocator.EXPORT_FILE)
				//.addAnyInstanceOf(aClass)
				.addClassParameter(aClass)
				.addParameters(parameters));
		if(Boolean.TRUE.equals(dynamic)){
			addUrl(roleCode,UniformResourceLocator.Builder.instanciateOne().setAddress(servletContext.getContextPath()
					+UniformResourceLocator.DYNAMIC_EXPORT_FILE_JASPER).addClassParameter(aClass).addParameters(parameters));	
		}else{
			addUrl(roleCode,UniformResourceLocator.Builder.instanciateOne().setAddress(servletContext.getContextPath()
					+UniformResourceLocator.EXPORT_FILE_JASPER).addClassParameter(aClass).addParameters(parameters));	
		}
		
		
	}
	
	protected void addReportUrl(String roleCode,Class<? extends AbstractIdentifiable> aClass,Object...parameters){
		addReportUrl(roleCode, aClass, Boolean.TRUE, parameters);
	}
	
}
