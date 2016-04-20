package org.cyk.ui.web.api;

import java.io.Serializable;

import javax.inject.Inject;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.apache.shiro.SecurityUtils;
import org.cyk.ui.api.AbstractUserSession;
import org.omnifaces.util.Faces;

public abstract class AbstractWebUserSession<NODE,MODEL extends WebHierarchyNode> extends AbstractUserSession<NODE,MODEL> implements HttpSessionBindingListener , Serializable {

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
	
	/**/
	
	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		
	}
	
	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		
	}
}
