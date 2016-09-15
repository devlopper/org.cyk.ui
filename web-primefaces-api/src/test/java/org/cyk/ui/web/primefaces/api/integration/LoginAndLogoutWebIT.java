package org.cyk.ui.web.primefaces.api.integration;

import org.cyk.ui.web.primefaces.test.automation.security.LoginAndLogoutWebITRunner;


public class LoginAndLogoutWebIT extends AbstractWebIT {

	private static final long serialVersionUID = 1L;
	
	public LoginAndLogoutWebIT() {
		runnables.add(new LoginAndLogoutWebITRunner());
	}
	
}
