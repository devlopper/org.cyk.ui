package org.cyk.ui.web.primefaces.api.integration;

import java.util.concurrent.TimeUnit;

import org.cyk.ui.web.primefaces.test.automation.SeleniumHelper;
import org.cyk.utility.test.integration.ui.web.AbstractIntegrationWebTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AbstractWebIT extends AbstractIntegrationWebTest {

	private static final long serialVersionUID = 1L;

	protected SeleniumHelper helper;

	{
		helper = new SeleniumHelper();
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
	
	/*protected WebElement getElementByClassContains(String value){
		return getDriver().findElement(By.cssSelector("[class*='"+value+"']"));
	}
	
	protected void login(String username,String password){
		getElementByClassContains("inputtext_nom_d_utilisateur_").sendKeys(username);
		getElementByClassContains("inputpassword_mot_de_passe_").sendKeys(password);
		getDriver().findElement(By.xpath("/html/body/div[1]/div[2]/form/div[1]/div/table/tbody/tr/td/div/div/button")).click();
	}
	
	protected void logout(String username){
		clickOnMenuItem(username,"Se deconnecter");
	}*/
	
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
	
	/*protected void clickOnMenuItem(String...labels){
		if(labels==null || labels.length==0)
			return;
		if(labels.length>1)
			for(int i=0;i<labels.length-1;i++)
				new Actions(getDriver()).moveToElement(getDriver().findElement(By.linkText(labels[i]))).build().perform();
        getDriver().findElement(By.linkText(labels[labels.length-1])).click();
	}*/
	
	protected void sendAutocompleteKeys(String elementClassPart,String value,String selectedXpath){
		helper.sendKeys(elementClassPart, value);
        getDriver().findElement(By.xpath(selectedXpath)).click();
	}
	
    @Override
	protected void __execute__() {
        getDriver().manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        
        
	}
    
    protected void clickFormSubmitButton(){
    	getDriver().findElement(By.xpath("/html/body/div[3]/div[2]/form/div[2]/div/button")).click();
    }
    
    protected void submitForm(){
    	clickFormSubmitButton();
        //
        clickFormOperationResultOkButton(Boolean.TRUE);
    }
    
    protected void clickFormOperationResultOkButton(Boolean isMessageDialog){
    	if(Boolean.TRUE.equals(isMessageDialog))
    		getDriver().findElement(By.xpath("//*[@id=\"form:messageDialogIdOkButton\"]/span[1]")).click();
    	else
    		getDriver().findElement(By.xpath("/html/body/div[11]/div[2]/table/tbody/tr[2]/td/div/button")).click();
    	
    }
    
    protected void clickFormOperationConfirmYesButton(){
    	getDriver().findElement(By.xpath("/html/body/div[11]/div[3]/button[1]")).click();
    }
    
    /**/
    
    protected void clickContextMenu(Integer actionIndex){
    	getDriver().findElement(By.xpath("/html/body/div[2]/form/div[1]/ul/li["+(actionIndex+1)+"]/a")).click();
    }
    protected void clickEditContextMenu(){
    	clickContextMenu(1);
    }
    protected void clickDeleteContextMenu(){
    	clickContextMenu(2);
    }
    
    /**/
    
    protected void actOnTableRow(Integer rowIndex,Integer actionIndex){
    	getDriver().findElement(By.xpath("/html/body/div[3]/div[2]/form/div[1]/div/div[2]/div[2]/table/tbody/tr["+rowIndex+"]/td[5]/table/tbody/tr/td["+actionIndex+"]/button")).click();
    }
    protected void showReadFormFromTable(Integer index){
    	actOnTableRow(index, 1);
    }
    protected void showUpdateFormFromTable(Integer index){
    	actOnTableRow(index, 2);
    }
    protected void showDeleteFormFromTable(Integer index){
    	actOnTableRow(index, 3);
    }
}
