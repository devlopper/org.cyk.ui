package org.cyk.ui.api;

import java.io.Serializable;

import javax.inject.Inject;

import lombok.Getter;

import org.cyk.system.root.business.api.security.PermissionBusiness;
import org.cyk.system.root.business.api.security.RoleBusiness;
import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractRoleManager<REQUEST> extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 3355588987277022257L;

	@Inject @Getter protected RoleBusiness roleBusiness;	
	@Inject @Getter protected PermissionBusiness permissionBusiness;
	
	public abstract Boolean isAdministrator(REQUEST request);
	
	public abstract Boolean isManager(REQUEST request);
	
	public abstract Boolean isSettingManager(REQUEST request);
	
	public abstract Boolean isSecurityManager(REQUEST request);
	
}
