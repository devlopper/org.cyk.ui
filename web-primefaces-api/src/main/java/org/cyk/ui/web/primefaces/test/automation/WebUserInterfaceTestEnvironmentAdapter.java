package org.cyk.ui.web.primefaces.test.automation;

import org.cyk.utility.common.test.WebUserInterfaceTestEnvironmentListener;
import org.openqa.selenium.WebDriver;

public class WebUserInterfaceTestEnvironmentAdapter extends WebUserInterfaceTestEnvironmentListener.Adapter {

	private static final long serialVersionUID = 1L;

	@Override
	public void setDriver(WebDriver driver) {
		super.setDriver(driver);
		SeleniumHelper.getInstance().setDriver(driver);
	}
	
	@Override
	public void login(String username,String password) {
		SeleniumHelper.getInstance().login(username, password);
	}
	
	@Override
	public void logout(String username) {
		SeleniumHelper.getInstance().logout(username);
	}
	
	@Override 
	public void goToLoginPage() {
		SeleniumHelper.getInstance().goToLoginPage(); 
	}
	
}
