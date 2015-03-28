package org.cyk.ui.web.api.security;

import java.io.Serializable;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;

import org.cyk.system.root.model.security.Role;
import org.cyk.ui.api.AbstractRoleManager;
import org.omnifaces.util.Faces;

@Named @Singleton
public class RoleManager extends AbstractRoleManager<HttpServletRequest> implements Serializable {

	private static final long serialVersionUID = 3355588987277022257L;

	private static RoleManager INSTANCE;
	
	//@Inject @Getter private RoleBusiness roleBusiness;	
	//@Inject @Getter private PermissionBusiness permissionBusiness;
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public Boolean isAdministrator(HttpServletRequest request){
		return Boolean.TRUE.equals(hasRole(request,Role.ADMINISTRATOR));
	}
	
	public Boolean isManager(HttpServletRequest request){
		return Boolean.TRUE.equals(hasRole(request, Role.MANAGER));
	}
	
	@Override
	public Boolean isSettingManager(HttpServletRequest request) {
		return Boolean.TRUE.equals(hasRole(request, Role.SETTING_MANAGER));
	}
	
	@Override
	public Boolean isSecurityManager(HttpServletRequest request) {
		return Boolean.TRUE.equals(hasRole(request, Role.SECURITY_MANAGER));
	}
	
	public Boolean isBusinessActor(HttpServletRequest request){
		return Boolean.TRUE.equals(hasRole(request, Role.BUSINESS_ACTOR));
	}
	
	/**/
	
	
	private <ROLE extends Role> Boolean hasRole(HttpServletRequest request,String roleCode){
		
		if(request==null)
			request = Faces.getRequest();
		return request.isUserInRole(roleBusiness.find(roleCode).getIdentifier().toString());
	}
	
	public static RoleManager getInstance() {
		return INSTANCE;
	}
	
}
