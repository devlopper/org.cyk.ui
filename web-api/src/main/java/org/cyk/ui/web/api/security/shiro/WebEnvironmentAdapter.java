package org.cyk.ui.web.api.security.shiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.datasource.DataSource;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.security.Role;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.web.api.security.RoleManager;
import org.cyk.utility.common.cdi.AbstractBean;

public class WebEnvironmentAdapter extends AbstractBean implements WebEnvironmentListener,Serializable {

	private static final long serialVersionUID = 4907315709148031667L;

	protected static final String FILTER_VAR = "user";
	protected static final String REALM_VAR = "realm";
	protected static final String CACHE_MANAGER_VAR = "cacheManager";
	protected static final String DATA_SOURCE_VAR = "dataSource";
	
	protected static final String ROLES_FORMAT = "%s , roles[%s]";
	protected static final String PERMISSIONS_FORMAT = "%s , perms[%s]";
	
	public static DataSource DATA_SOURCE;
	public static final Collection<SecuredUrlProvider> SECURED_URL_PROVIDERS = new ArrayList<>();
	
	
	private Section  urlsSection;
	
	public WebEnvironmentAdapter() {
		SECURED_URL_PROVIDERS.add(new RootSecuredUrlProvider());
	}
	
	@Override
	public void ini(WebEnvironment environment, Ini anIni) {
		Section  main = anIni.addSection("main");
		main.put(FILTER_VAR,"org.cyk.ui.web.api.security.shiro.AuthorisationFilter");
		main.put(REALM_VAR, "org.cyk.ui.web.api.security.shiro.Realm");
		main.put(CACHE_MANAGER_VAR, "org.apache.shiro.cache.ehcache.EhCacheManager");
				
		main.put("securityManager.cacheManager", "$"+CACHE_MANAGER_VAR);		
		main.put("dataSource", DATA_SOURCE.getDriver());
		main.put("dataSource.URL", DATA_SOURCE.getUrl());
		main.put("dataSource.user", DATA_SOURCE.getUsername());
		main.put("dataSource.password", DATA_SOURCE.getPassword());
		
		main.put(REALM_VAR+".dataSource", "$dataSource");
		
		urlsSection = anIni.addSection("urls");
		urlsSection.put("/login", "user");
		
		for(SecuredUrlProvider securedUrlProvider : SECURED_URL_PROVIDERS)
			for(Entry<String, String> entry : securedUrlProvider.urls.entrySet())
				urlsSection.put(entry.getKey(), entry.getValue());
		
		roleUser();

		
		logInfo(anIni.toString());
		//debug(urlsSection);
	}
	
	/**/
	
	protected void roleUser(){
		WebEnvironmentAdapter.role(urlsSection,"/private/**", Role.USER);//TODO content should be moved to desktop or mobile and this removed
		WebEnvironmentAdapter.role(urlsSection,"/mobile/private/**", Role.USER);
		WebEnvironmentAdapter.role(urlsSection,"/desktop/private/**", Role.USER);
	}
	
	protected class RootSecuredUrlProvider extends SecuredUrlProvider implements Serializable {

		private static final long serialVersionUID = -4399583055230412704L;

		@Override
		public void provide() {
			roleSettingManager();
			roleSecurityManager();
			roleManager();
			roleAdministrator();
			roleBusinessActor();
		}
		
		protected void roleAdministrator(){
			roleFolder("__administrator__", Role.ADMINISTRATOR);
		}
		
		protected void roleManager(){
			roleFolder("__manager__",Role.MANAGER);
		}
		
		protected void roleSecurityManager(){
			roleFolder("__securitymanager__",Role.SECURITY_MANAGER);
		}
		
		protected void roleSettingManager(){
			//permission("/private/__role__/__settingmanager__/readlicense.jsf",License.class, Crud.READ);
			roleFolder("__settingmanager__",Role.SETTING_MANAGER);
		}
		
		protected void roleBusinessActor(){
			roleFolder("__businessactor__", Role.BUSINESS_ACTOR);
		}
		
	}
	
	/**/
	
	
	
	/**/
	
	protected static void role(Map<String,String> map,String path,String roleCode){
		if(UIManager.getInstance().getApplicationBusiness().findCurrentInstance()==null)
			return;
		/*
		System.out.println("WebEnvironmentAdapter.role() : 0 "+RoleManager.getInstance());
		System.out.println("WebEnvironmentAdapter.role() : 1 "+RoleManager.getInstance().getRoleBusiness());
		Role role = RoleManager.getInstance().getRoleBusiness().find(roleCode);
		System.out.println("WebEnvironmentAdapter.role() : 2 "+role);
		*/
		map.put(path,String.format(ROLES_FORMAT,FILTER_VAR,RoleManager.getInstance().getRoleBusiness().find(roleCode).getIdentifier()));
	}
	
	/**/
	
	public static abstract class SecuredUrlProvider{
		private Map<String,String> urls = new LinkedHashMap<String, String>();
		
		public abstract void provide();
		
		protected void role(String path,String roleCode){
			WebEnvironmentAdapter.role(urls,path,roleCode);
			/*
			if(UIManager.getInstance().getApplicationBusiness().findCurrentInstance()==null)
				return;
			urls.put(path,String.format(ROLES_FORMAT,FILTER_VAR,RoleManager.getInstance().getRoleBusiness().find(roleCode).getIdentifier()));
			*/
		}
		
		protected void roleFolder(String folderPath,String roleCode){
			role("/private/__role__/"+folderPath+"/**", roleCode);
		}
		
		protected void permission(String path,String permission){
			urls.put(path,String.format(PERMISSIONS_FORMAT,FILTER_VAR,permission));
		}
		
		protected void permission(String path,Class<? extends AbstractIdentifiable> aClass,Crud aCrud){
			if(UIManager.getInstance().getApplicationBusiness().findCurrentInstance()==null)
				return;
			permission(path, RoleManager.getInstance().getPermissionBusiness().find(aClass, aCrud).getIdentifier().toString());
		}
	}

}
