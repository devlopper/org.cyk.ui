package org.cyk.ui.web.primefaces.api.integration;

import java.util.concurrent.TimeUnit;

import org.cyk.ui.api.CascadeStyleSheet;
import org.cyk.ui.web.primefaces.test.automation.ContextMenu;
import org.cyk.ui.web.primefaces.test.automation.GlobalMenu;
import org.cyk.ui.web.primefaces.test.automation.SeleniumHelper;
import org.cyk.ui.web.primefaces.test.automation.Table;
import org.cyk.utility.test.integration.ui.web.AbstractIntegrationWebTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AbstractWebIT extends AbstractIntegrationWebTest {

	private static final long serialVersionUID = 1L;

	protected SeleniumHelper helper;

	{
		helper = SeleniumHelper.getInstance();
		helper.setScheme(scheme);
		helper.setContext(context);
		helper.setHost(host);
		helper.setPort(port);
		authenticationEnabled = Boolean.TRUE;
		users = new String[][]{ {"admin","123"} };
		helper.setContext("gui-primefaces");
	}
	
	@Override
	protected void setDriver(WebDriver driver) {
		super.setDriver(driver);
		helper.setDriver(driver);
	}
	
	@Override
	protected void login(String username,String password) {
		helper.login(username, password);
	}
	
	@Override
	protected void logout(String username) {
		helper.logout(username);
	}
	
	@Override 
	protected void goToLoginPage() {
		helper.goToLoginPage(); 
	}
	
	protected void pause(Long numberOfMillisecond){
		try {
			Thread.sleep(numberOfMillisecond);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	protected void waitForClickOn(String xpath,Long timeOutInSecond){
		WebDriverWait wait = new WebDriverWait(getDriver(),timeOutInSecond);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
	}
	
	protected void sendAutocompleteKeys(String elementClassPart,String value,String selectedXpath){
		helper.sendKeys(elementClassPart, value);
        getDriver().findElement(By.xpath(selectedXpath)).click();
	}
	
    @Override
	protected void __execute__() {
        getDriver().manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        
        
	}
    
    /**/
    protected void clickGlobalMenu(String...labels){
    	new GlobalMenu().click(labels);
    }
    
    protected void clickContextualMenuEdit(){
    	new ContextMenu(CascadeStyleSheet.CONTEXTUAL_MENU_CLASS).clickEdit();
    }
    protected void clickContextualMenuDelete(){
    	new ContextMenu(CascadeStyleSheet.CONTEXTUAL_MENU_CLASS).clickDelete();
    }
    
    protected void clickTableCreate(){
    	new Table("dataTableStyleClass").clickCreate();
    }
    
    protected void clickTableRead(Integer index){
    	new Table("dataTableStyleClass").clickRead(index);
    }
}
