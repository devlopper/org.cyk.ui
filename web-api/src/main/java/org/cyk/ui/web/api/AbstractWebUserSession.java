package org.cyk.ui.web.api;

import java.io.Serializable;

import javax.inject.Inject;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.apache.shiro.SecurityUtils;
import org.cyk.ui.api.AbstractUserSession;
import org.cyk.ui.web.api.security.RoleManager;
import org.omnifaces.util.Faces;

public abstract class AbstractWebUserSession extends AbstractUserSession implements HttpSessionBindingListener , Serializable {

	private static final long serialVersionUID = 7799444210756287076L;

	@Inject transient protected WebNavigationManager navigationManager; 
	
	@Override
	protected void __logout__() {
		SecurityUtils.getSubject().logout();
	}

	@Override
	protected void __navigateToPublicIndex__() {
		navigationManager.redirectTo(WebNavigationManager.getInstance().getOutcomePrivateIndex());
	}

	@Override
	protected void __invalidateSession__() {
		Faces.invalidateSession();
	}

	@Override
	public Boolean getIsAdministrator() {
		return RoleManager.getInstance().isAdministrator(null);
	}

	@Override
	public Boolean getIsManager() {
		return RoleManager.getInstance().isManager(null);
	}
	
	/**/
	
	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		
	}
	
	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		
	}
}
