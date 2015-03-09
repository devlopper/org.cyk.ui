package org.cyk.ui.web.api;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.BusinessManager;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.MenuListener;
import org.cyk.ui.api.MenuManager;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.UserSession;
import org.cyk.ui.api.MenuManager.Type;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.menu.UIMenu;
import org.cyk.ui.api.config.IdentifiableConfiguration;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
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
	
	/**/
	
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
	
	@Override
	public void menu(UserSession userSession, UIMenu menu, Type type) {
			
	}
	
	@Override
	public UICommandable module(UserSession userSession, UIMenu menu,UICommandable module, ModuleType type) {
		return module;
	}
	
	@Override
	public Collection<UICommandable> modules(UserSession userSession,UIMenu aMenu, Collection<UICommandable> modules, ModuleType type) {
		return modules;
	}
	
	/**/
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
	}
	
}
