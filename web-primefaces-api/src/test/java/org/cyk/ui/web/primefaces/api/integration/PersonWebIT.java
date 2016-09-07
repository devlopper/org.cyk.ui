package org.cyk.ui.web.primefaces.api.integration;

import java.util.concurrent.TimeUnit;

import org.cyk.utility.test.integration.ui.web.AbstractIntegrationWebTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class PersonWebIT extends AbstractIntegrationWebTest {

	private static final long serialVersionUID = 1L;

	protected void login(WebDriver webDriver,String username,String password){
		webDriver.findElement(By.xpath("/html/body/div[1]/div[2]/form/div[1]/div/table/tbody/tr/td/div/div/table[3]/tbody/tr[3]/td[2]/table/tbody/tr/td[1]/input")).sendKeys(username);
        webDriver.findElement(By.xpath("/html/body/div[1]/div[2]/form/div[1]/div/table/tbody/tr/td/div/div/table[3]/tbody/tr[4]/td[2]/table/tbody/tr/td[1]/input")).sendKeys(password);
        webDriver.findElement(By.xpath("/html/body/div[1]/div[2]/form/div[1]/div/table/tbody/tr/td/div/div/button")).click();
	}
	
	protected void logout(WebDriver webDriver,String username){
		clickOnMenuItem(webDriver, username,"Se deconnecter");
	}
	
	protected void pause(Long numberOfMillisecond){
		try {
			Thread.sleep(numberOfMillisecond);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	protected void clickOnMenuItem(WebDriver webDriver,String...labels){
		if(labels==null || labels.length==0)
			return;
		if(labels.length>1)
			for(int i=0;i<labels.length-1;i++)
				new Actions(webDriver).moveToElement(webDriver.findElement(By.linkText(labels[i]))).build().perform();
        webDriver.findElement(By.linkText(labels[labels.length-1])).click();
	}
	
	protected void sendKeys(WebDriver webDriver,String xpath,String value){
		webDriver.findElement(By.xpath(xpath)).clear();
        webDriver.findElement(By.xpath(xpath)).sendKeys(value);
	}
	protected void sendAutocompleteKeys(WebDriver webDriver,String xpath,String value,String selectedXpath){
		sendKeys(webDriver, xpath, value);
		pause(1 * 1000l);
        webDriver.findElement(By.xpath(selectedXpath)).click();
	}
	
    @Override
	protected void __execute__(WebDriver webDriver) {
        webDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        //webDriver.manage().window().maximize();
        webDriver.get("http://localhost:8080/gui-primefaces/private/administrator.jsf");
        login(webDriver, "admin", "123");
        clickOnMenuItem(webDriver, "PARTY","Lister personne");
        
        webDriver.findElement(By.xpath("/html/body/div[3]/div[2]/form/div[1]/div/div[2]/div[1]/div/div[1]/button")).click();
        
        fillForm(webDriver,Boolean.FALSE,"pers0011","yao","evelyne","D:\\Hydrangeas.jpg");        
        submitForm(webDriver);
        showReadFormFromTable(webDriver, 2);
        pause(5 * 1000l);
        
        clickEditContextMenu(webDriver);
        //showUpdateFormFromTable(webDriver, 2);
        fillForm(webDriver,Boolean.TRUE,"PA021","Zouzou","Ange","D:\\Jellyfish.jpg");
        submitForm(webDriver);
        pause(5 * 1000l);
        
        clickDeleteContextMenu(webDriver);
        //showDeleteFormFromTable(webDriver, 2);
        clickFormSubmitButton(webDriver);
        clickFormOperationConfirmYesButton(webDriver);
        clickFormOperationResultOkButton(webDriver,Boolean.FALSE);
        
        logout(webDriver,"admin");
     
	}
    
    protected void fillForm(WebDriver webDriver,Boolean hasImage,String code,String firstname,String lastnames,String imagePath){
        sendKeys(webDriver, "/html/body/div[3]/div[2]/form/div[1]/div/table[2]/tbody/tr[11]/td[2]/table/tbody/tr/td[1]/input", code);
        sendKeys(webDriver, "/html/body/div[3]/div[2]/form/div[1]/div/table[2]/tbody/tr[12]/td[2]/table/tbody/tr/td[1]/input", firstname);
        sendKeys(webDriver, "/html/body/div[3]/div[2]/form/div[1]/div/table[2]/tbody/tr[13]/td[2]/table/tbody/tr/td[1]/input", lastnames);
        
        webDriver.findElement(By.xpath("/html/body/div[3]/div[2]/form/div[1]/div/table[2]/tbody/tr[14]/td[2]/table/tbody/tr/td[1]/table/tbody/tr/td[1]/div/div[2]/span")).click();
        
        webDriver.findElement(By.xpath("/html/body/div[3]/div[2]/form/div[1]/div/table[2]/tbody/tr[15]/td[2]/table/tbody/tr/td[1]/span/input")).click();
        webDriver.findElement(By.linkText("5")).click();
        
        pause(1 * 1000l);
        sendAutocompleteKeys(webDriver, "/html/body/div[3]/div[2]/form/div[1]/div/table[2]/tbody/tr[16]/td[2]/table/tbody/tr/td[1]/span/input[1]", "ab"
        		,"/html/body/div[10]/ul/li[2]");
       
        sendAutocompleteKeys(webDriver, "/html/body/div[3]/div[2]/form/div[1]/div/table[2]/tbody/tr[17]/td[2]/table/tbody/tr/td[1]/span/input[1]", "cot"
        		,"/html/body/div[11]/ul/li");
        
        sendKeys(webDriver, "/html/body/div[3]/div[2]/form/div[1]/div/table[2]/tbody/tr[18]/td[2]/table/tbody/tr[2]/td[1]/table/tbody/tr[1]/td/input"
        		, imagePath);
        
        webDriver.findElement(By.xpath("/html/body/div[3]/div[2]/form/div[1]/div/table[2]/tbody/tr[19]/td[2]/table/tbody/tr/td[1]/table/tbody/tr/td[5]/div/div[2]/span")).click();
        
        sendAutocompleteKeys(webDriver, "/html/body/div[3]/div[2]/form/div[1]/div/table[2]/tbody/tr[20]/td[2]/table/tbody/tr/td[1]/span/input[1]", "fr"
        		,"/html/body/div["+(Boolean.TRUE.equals(hasImage) ? 14 : 12)+"]/ul/li[1]");
  
    }

    protected void clickFormSubmitButton(WebDriver webDriver){
    	webDriver.findElement(By.xpath("/html/body/div[3]/div[2]/form/div[2]/div/button")).click();
    }
    
    protected void submitForm(WebDriver webDriver){
    	clickFormSubmitButton(webDriver);
        //
        clickFormOperationResultOkButton(webDriver,Boolean.TRUE);
    }
    
    protected void clickFormOperationResultOkButton(WebDriver webDriver,Boolean isMessageDialog){
    	if(Boolean.TRUE.equals(isMessageDialog))
    		webDriver.findElement(By.xpath("//*[@id=\"form:messageDialogIdOkButton\"]/span[1]")).click();
    	else
    		webDriver.findElement(By.xpath("/html/body/div[11]/div[2]/table/tbody/tr[2]/td/div/button")).click();
    	
    }
    
    protected void clickFormOperationConfirmYesButton(WebDriver webDriver){
    	webDriver.findElement(By.xpath("/html/body/div[11]/div[3]/button[1]")).click();
    }
    
    /**/
    
    protected void clickContextMenu(WebDriver webDriver,Integer actionIndex){
    	webDriver.findElement(By.xpath("/html/body/div[2]/form/div[1]/ul/li["+(actionIndex+1)+"]/a")).click();
    }
    protected void clickEditContextMenu(WebDriver webDriver){
    	clickContextMenu(webDriver, 1);
    }
    protected void clickDeleteContextMenu(WebDriver webDriver){
    	clickContextMenu(webDriver, 2);
    }
    
    /**/
    
    protected void actOnTableRow(WebDriver webDriver,Integer rowIndex,Integer actionIndex){
    	webDriver.findElement(By.xpath("/html/body/div[3]/div[2]/form/div[1]/div/div[2]/div[2]/table/tbody/tr["+rowIndex+"]/td[5]/table/tbody/tr/td["+actionIndex+"]/button")).click();
    }
    protected void showReadFormFromTable(WebDriver webDriver,Integer index){
    	actOnTableRow(webDriver, index, 1);
    }
    protected void showUpdateFormFromTable(WebDriver webDriver,Integer index){
    	actOnTableRow(webDriver, index, 2);
    }
    protected void showDeleteFormFromTable(WebDriver webDriver,Integer index){
    	actOnTableRow(webDriver, index, 3);
    }
}
