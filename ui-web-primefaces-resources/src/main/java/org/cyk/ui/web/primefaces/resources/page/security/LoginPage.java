package org.cyk.ui.web.primefaces.resources.page.security;

import java.io.Serializable;
import java.net.URI;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.cyk.ui.web.api.resources.WebResourcesManager;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.UniformResourceLocatorHelper;
import org.cyk.utility.common.userinterface.UserSessionHelper;
import org.cyk.utility.common.userinterface.container.window.LoginWindow;
import org.omnifaces.util.Faces;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class LoginPage extends LoginWindow implements Serializable {
	private static final long serialVersionUID = 1L;

	static {
		ClassHelper.getInstance().map(LoginWindow.FormMaster.SubmitCommandActionAdapter.class, SubmitCommandActionAdapter.class);
	}
		
	public static class SubmitCommandActionAdapter extends LoginWindow.FormMaster.SubmitCommandActionAdapter implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@Override
		protected void processOnSuccess() {
			super.processOnSuccess();
			SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(Faces.getRequest());
			String url = null;
			if(savedRequest==null || StringUtils.equals(URI.create(savedRequest.getRequestURI()).getPath(), Faces.getRequestContextPath()+"/private/index.jsf"))
				url = UserSessionHelper.getInstance().getUniformResourceLocator(UniformResourceLocatorHelper.Identifier.HOME);
			else
				url = savedRequest.getRequestUrl();
			WebResourcesManager.getInstance().redirect(url);
		}
		
	}
	
}
