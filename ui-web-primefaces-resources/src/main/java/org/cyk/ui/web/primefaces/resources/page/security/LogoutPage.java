package org.cyk.ui.web.primefaces.resources.page.security;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.ui.web.api.resources.WebResourcesManager;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.UniformResourceLocatorHelper;
import org.cyk.utility.common.userinterface.UserSessionHelper;
import org.cyk.utility.common.userinterface.container.window.LogoutWindow;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class LogoutPage extends LogoutWindow implements Serializable {
	private static final long serialVersionUID = 1L;

	static {
		ClassHelper.getInstance().map(LogoutWindow.FormMaster.SubmitCommandActionAdapter.class, SubmitCommandActionAdapter.class);
	}
	
	public static class SubmitCommandActionAdapter extends LogoutWindow.FormMaster.SubmitCommandActionAdapter implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@Override
		protected void processOnSuccess() {
			super.processOnSuccess();
			String url = UserSessionHelper.getInstance().getUniformResourceLocator(UniformResourceLocatorHelper.Identifier.HOME);
			WebResourcesManager.getInstance().redirect(url);
		}
		
	}
	
}
