package org.cyk.ui.web.api.security;

import java.io.Serializable;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;

import org.cyk.system.root.model.RootConstant;
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
		return Boolean.TRUE.equals(hasRole(request,RootConstant.Code.Role.ADMINISTRATOR));
	}
	
	public Boolean isManager(HttpServletRequest request){
		return Boolean.TRUE.equals(hasRole(request, RootConstant.Code.Role.MANAGER));
	}
	
	@Override
	public Boolean isSettingManager(HttpServletRequest request) {
		return Boolean.TRUE.equals(hasRole(request, RootConstant.Code.Role.SETTING_MANAGER));
	}
	
	@Override
	public Boolean isSecurityManager(HttpServletRequest request) {
		return Boolean.TRUE.equals(hasRole(request, RootConstant.Code.Role.SECURITY_MANAGER));
	}
	
	/**/
	
	public <ROLE extends Role> Boolean hasRole(HttpServletRequest request,String roleCode){
		
		if(request==null)
			request = Faces.getRequest();
		return request.isUserInRole(roleBusiness.findByGlobalIdentifierCode(roleCode).getIdentifier().toString());
	}
	
	public <ROLE extends Role> Boolean hasRole(String roleCode){
		return hasRole(null, roleCode);
	}
	
	public static RoleManager getInstance() {
		return INSTANCE;
	}
	
}
