package org.cyk.ui.web.api.security.shiro;

import java.io.Serializable;

import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.datasource.DataSource;
import org.cyk.system.root.business.api.security.PermissionBusiness;
import org.cyk.system.root.business.api.security.RoleBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.security.License;
import org.cyk.system.root.model.security.Role;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.web.api.security.RoleManager;
import org.cyk.utility.common.cdi.AbstractBean;

public class WebEnvironmentAdapter extends AbstractBean implements WebEnvironmentListener,Serializable {

	private static final long serialVersionUID = 4907315709148031667L;

	protected static final String FILTER_VAR = "user";
	protected static final String REALM_VAR = "realm";
	protected static final String DATA_SOURCE_VAR = "dataSource";
	
	protected static final String ROLES_FORMAT = "%s , roles[%s]";
	protected static final String PERMISSIONS_FORMAT = "%s , perms[%s]";
	
	public static DataSource DATA_SOURCE;
	
	private RoleBusiness roleBusiness;
	private PermissionBusiness permissionBusiness;
	private Section  urlsSection;
	
	public WebEnvironmentAdapter() {
		permissionBusiness = RoleManager.getInstance().getPermissionBusiness();
		roleBusiness = RoleManager.getInstance().getRoleBusiness();
	}
	
	@Override
	public void ini(WebEnvironment environment, Ini anIni) {
		Section  main = anIni.addSection("main");
		main.put(FILTER_VAR,"org.cyk.ui.web.api.security.shiro.AuthorisationFilter");
		main.put(REALM_VAR, "org.cyk.ui.web.api.security.shiro.Realm");
		
		main.put("dataSource", DATA_SOURCE.getDriver());
		main.put("dataSource.URL", DATA_SOURCE.getUrl());
		main.put("dataSource.user", DATA_SOURCE.getUsername());
		main.put("dataSource.password", DATA_SOURCE.getPassword());
		
		main.put(REALM_VAR+".dataSource", "$dataSource");
		
		urlsSection = anIni.addSection("urls");
		urlsSection.put("/login", "user");
		
		roleSettingManager();
		roleSecurityManager();
		roleManager();
		
		roleBusinessActor();
		
		/**/
		
		roleAdministrator();
		//debug(anIni);
		//debug(urlsSection);
	}
	
	/**/
	
	protected void roleAdministrator(){
		role("/private/__role__/__administrator__/**", Role.ADMINISTRATOR);
	}
	
	protected void roleManager(){
		role("/private/__role__/__manager__/**",Role.MANAGER);
	}
	
	protected void roleSecurityManager(){
		role("/private/__role__/__securitymanager__/**",Role.SECURITY_MANAGER);
	}
	
	protected void roleSettingManager(){
		//permission("/private/__role__/__settingmanager__/readlicense.jsf",License.class, Crud.READ);
		role("/private/__role__/__settingmanager__/**",Role.SETTING_MANAGER);
	}
	
	protected void roleBusinessActor(){
		role("/private/**", Role.BUSINESS_ACTOR);
	}
	
	/**/
	
	protected void role(String path,String roleCode){
		if(UIManager.getInstance().getApplicationBusiness().findCurrentInstance()==null)
			return;
		urlsSection.put(path,String.format(ROLES_FORMAT,FILTER_VAR,roleBusiness.find(roleCode).getIdentifier()));
	}
	
	protected void permission(String path,String permission){
		urlsSection.put(path,String.format(PERMISSIONS_FORMAT,FILTER_VAR,permission));
	}
	
	protected void permission(String path,Class<? extends AbstractIdentifiable> aClass,Crud aCrud){
		if(UIManager.getInstance().getApplicationBusiness().findCurrentInstance()==null)
			return;
		permission(path, permissionBusiness.find(aClass, aCrud).getIdentifier().toString());
	}

}
