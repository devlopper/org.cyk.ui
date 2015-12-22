package org.cyk.ui.web.api.security.shiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import lombok.Getter;
import lombok.Setter;

import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.datasource.DataSource;
import org.cyk.system.root.business.api.network.UniformResourceLocatorBusiness;
import org.cyk.system.root.business.api.security.RoleUniformResourceLocatorBusiness;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.RoleUniformResourceLocator;
import org.cyk.ui.web.api.security.RoleManager;
import org.cyk.utility.common.cdi.AbstractBean;

public interface WebEnvironmentListener {

	Collection<SecuredUrlProvider> SECURED_URL_PROVIDERS = new ArrayList<>();
	
	void ini(WebEnvironment environment,Ini anIni);
	
	
	/**/
	public static abstract class SecuredUrlProvider{
		private Map<String,String> urls = new LinkedHashMap<String, String>();
		
		public abstract void provide();
		
		protected void role(String path,String roleCode){
			Adapter.role(urls,path,roleCode);
		}
		
		protected void roleFolder(String folderPath,String roleCode){
			role("/private/__role__/"+folderPath+"/**", roleCode);
		}
		
		protected void permission(String path,String permission){
			urls.put(path,String.format(PERMISSIONS_FORMAT,FILTER_VAR,permission));
		}
		
		protected void permission(String path,Class<? extends AbstractIdentifiable> aClass,Crud aCrud){
			if(RootBusinessLayer.getInstance().getApplication()==null)
				return;
			permission(path, RoleManager.getInstance().getPermissionBusiness().find(aClass, aCrud).getIdentifier().toString());
		}
	}
	/**/
	
	String FILTER_VAR = "user";
	String REALM_VAR = "realm";
	String CACHE_MANAGER_VAR = "cacheManager";
	String DATA_SOURCE_VAR = "dataSource";
	
	String AUTHORISATION_FILTER = "org.cyk.ui.web.api.security.shiro.AuthorisationFilter";
	String REALM = "org.cyk.ui.web.api.security.shiro.Realm";
	String CACHE_MANAGER = "org.apache.shiro.cache.ehcache.EhCacheManager";
	
	String ROLES_FORMAT = "%s , roles[%s]";
	String PERMISSIONS_FORMAT = "%s , perms[%s]";
	
	/**/
	@Getter @Setter
	public static class Adapter extends AbstractBean implements WebEnvironmentListener,Serializable {
		private static final long serialVersionUID = 1893780550150629316L;
		public static DataSource DATA_SOURCE;
		protected Section urlsSection;
		
		@Override
		public void ini(WebEnvironment environment, Ini anIni) {}
		
		protected static void role(Map<String,String> map,String path,String roleCode){
			role(map,path,RoleManager.getInstance().getRoleBusiness().find(roleCode));
		}
		
		protected static void role(Map<String,String> map,String path,Role role){
			if(RootBusinessLayer.getInstance().getApplication()==null)
				return;
			map.put(path,String.format(ROLES_FORMAT,FILTER_VAR,role.getIdentifier()));
		}
		
		/**/
		@Getter @Setter
		public static class Default extends Adapter implements Serializable {
			private static final long serialVersionUID = 4907315709148031667L;
			
			@Override
			public void ini(WebEnvironment environment, Ini anIni) {
				Section  main = anIni.addSection("main");
				main.put(FILTER_VAR,AUTHORISATION_FILTER);
				main.put(REALM_VAR, REALM);
				main.put(CACHE_MANAGER_VAR, CACHE_MANAGER);
						
				main.put("securityManager."+CACHE_MANAGER_VAR, "$"+CACHE_MANAGER_VAR);		
				main.put(DATA_SOURCE_VAR, DATA_SOURCE.getDriver());
				main.put(DATA_SOURCE_VAR+".URL", DATA_SOURCE.getUrl());
				main.put(DATA_SOURCE_VAR+".user", DATA_SOURCE.getUsername());
				main.put(DATA_SOURCE_VAR+".password", DATA_SOURCE.getPassword());
				
				main.put(REALM_VAR+"."+DATA_SOURCE_VAR, "$"+DATA_SOURCE_VAR);
				
				urlsSection = anIni.addSection("urls");
				urlsSection.put("/login", "user");
				
				for(SecuredUrlProvider securedUrlProvider : SECURED_URL_PROVIDERS)
					for(Entry<String, String> entry : securedUrlProvider.urls.entrySet())
						urlsSection.put(entry.getKey(), entry.getValue());
				
				/*
				for(RoleSecuredView roleSecuredView : UIManager.getInstance().getRoleSecuredViewBusiness().findAll())
					role(urlsSection,roleSecuredView.getViewId(), roleSecuredView.getAccessor());
				*/
				
				RoleUniformResourceLocatorBusiness roleUniformResourceLocatorBusiness = RootBusinessLayer.getInstance().getRoleUniformResourceLocatorBusiness();
				UniformResourceLocatorBusiness uniformResourceLocatorBusiness = RootBusinessLayer.getInstance().getUniformResourceLocatorBusiness();
				
				for(RoleUniformResourceLocator roleUniformResourceLocator : roleUniformResourceLocatorBusiness.findAll())
					role(urlsSection,uniformResourceLocatorBusiness.findPath(roleUniformResourceLocator.getUniformResourceLocator())
							, roleUniformResourceLocator.getRole());

				// TODO this is a trick to handle role ordering. Instead try using sorting. if A and B have common prefix then if A followed by ** then B comes first then A
				// B = common_prefix/a_sub_space
				// A = common_prefix/**
				urlsSection.remove("/private/**");
				urlsSection.put("/private/**", "user");
				
				logInfo("Secured views");
				for(Entry<String, String> entry : urlsSection.entrySet())
					logInfo(entry);
			}			
			
			/**/
			
		}
		
		
	}
	
}
