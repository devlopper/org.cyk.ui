package org.cyk.ui.web.primefaces.api.integration;

import java.util.concurrent.TimeUnit;

import org.cyk.utility.test.integration.ui.web.AbstractIntegrationWebTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class PersonWebITOLD extends AbstractIntegrationWebTest {

	private static final long serialVersionUID = 1L;

	protected void login(WebDriver webDriver,String username,String password){
		webDriver.findElement(By.id("form:j_idt77:j_idt84:j_idt86:j_idt423:r3c2reg:inputText")).click();
        webDriver.findElement(By.id("form:j_idt77:j_idt84:j_idt86:j_idt423:r3c2reg:inputText")).sendKeys(username);
        webDriver.findElement(By.id("form:j_idt77:j_idt84:j_idt86:j_idt423:r4c2reg:inputPassword")).click();
        webDriver.findElement(By.id("form:j_idt77:j_idt84:j_idt86:j_idt423:r4c2reg:inputPassword")).sendKeys(password);
        webDriver.findElement(By.id("form:j_idt77:j_idt752:j_idt752Button")).click();
	}
	
    @Override
	protected void __execute__(WebDriver webDriver) {
    	/*
    	webDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    	webDriver.get("https://www.google.com/");
    	webDriver.findElement(By.id("lst-ib")).click();
    	webDriver.findElement(By.id("lst-ib")).clear();
    	webDriver.findElement(By.id("lst-ib")).sendKeys("hello world");
    	webDriver.findElement(By.id("lst-ib")).click();
    	webDriver.findElement(By.id("lst-ib")).sendKeys("\n");
    	webDriver.findElement(By.id("slim_appbar")).click();
    	webDriver.findElement(By.linkText("\"Hello, World!\" program - Wikipedia, the free encyclopedia")).click();
    	webDriver.findElement(By.id("mw-content-text")).click();
    	webDriver.findElement(By.linkText("5 References")).click();
    	*/
    	
        webDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        //webDriver.manage().window().maximize();
        webDriver.get("http://localhost:8080/gui-primefaces/private/administrator.jsf");
        login(webDriver, "admin", "123");
       
        try {
			Thread.sleep(1000 * 3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //System.out.println("PersonWebIT.__execute__() : "+By.xpath("/html/body/div[1]/form/div/ul/li[5]/ul/li[1]/a/span"));
        new Actions(webDriver).moveToElement(webDriver.findElement(By.linkText("PARTY"))).build().perform();
        try {
			Thread.sleep(1000 * 3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //new Actions(webDriver).moveToElement(webDriver.findElement(By.xpath("//div[@id='formNorth:menubar']/ul/li[5]/ul"))).build().perform();
        //new Actions(webDriver).moveToElement(webDriver.findElement(By.linkText("Lister personne"))).build().perform();
        
      //Create an action object called myMouse
      	//Actions myMouse = new Actions(webDriver); 
        
        webDriver.findElement(By.xpath("/html/body/div[1]/form/div/ul/li[5]/ul/li[1]/a/span")).click();
        webDriver.findElement(By.id("form:j_idt72:dataTable:j_idt76:j_idt76Button")).click();
        webDriver.findElement(By.id("form:contentPanel")).click();
        webDriver.findElement(By.id("form:contentPanel")).click();
        webDriver.findElement(By.id("form:contentPanel")).click();
        webDriver.findElement(By.id("form:j_idt72:j_idt79:j_idt81:j_idt418:r11c2reg:inputText")).click();
        webDriver.findElement(By.id("form:j_idt72:j_idt79:j_idt81:j_idt418:r11c2reg:inputText")).clear();
        webDriver.findElement(By.id("form:j_idt72:j_idt79:j_idt81:j_idt418:r11c2reg:inputText")).sendKeys("pers001");
        webDriver.findElement(By.id("form:j_idt72:j_idt79:j_idt81:j_idt418:r12c2reg:inputText")).click();
        webDriver.findElement(By.id("form:j_idt72:j_idt79:j_idt81:j_idt418:r12c2reg:inputText")).clear();
        webDriver.findElement(By.id("form:j_idt72:j_idt79:j_idt81:j_idt418:r12c2reg:inputText")).sendKeys("yao");
        webDriver.findElement(By.id("form:j_idt72:j_idt79:j_idt81:j_idt418:r13c2reg:inputText")).click();
        webDriver.findElement(By.id("form:j_idt72:j_idt79:j_idt81:j_idt418:r13c2reg:inputText")).clear();
        webDriver.findElement(By.id("form:j_idt72:j_idt79:j_idt81:j_idt418:r13c2reg:inputText")).sendKeys("evelyne");
        webDriver.findElement(By.xpath("//table[@id='form:j_idt72:j_idt79:j_idt81:j_idt418:r14c2reg:inputOneRadio']/tbody/tr/td[3]/div/div[2]/span")).click();
        webDriver.findElement(By.id("form:j_idt72:j_idt79:j_idt81:j_idt418:r14c2reg:inputOneRadio:1")).click();
        webDriver.findElement(By.id("form:j_idt72:j_idt79:j_idt81:j_idt418:r15c2reg:inputCalendar_input")).click();
        webDriver.findElement(By.linkText("5")).click();
        webDriver.findElement(By.id("form:j_idt72:j_idt79:j_idt81:j_idt418:r16c2reg:inputOneAutoComplete_input")).click();
        webDriver.findElement(By.id("form:j_idt72:j_idt79:j_idt81:j_idt418:r16c2reg:inputOneAutoComplete_input")).clear();
        webDriver.findElement(By.id("form:j_idt72:j_idt79:j_idt81:j_idt418:r16c2reg:inputOneAutoComplete_input")).sendKeys("ab");
        webDriver.findElement(By.xpath("//div[@id='form:j_idt72:j_idt79:j_idt81:j_idt418:r16c2reg:inputOneAutoComplete_panel']//span[.='Ab']")).click();
        webDriver.findElement(By.id("form:j_idt72:j_idt79:j_idt81:j_idt418:r17c2reg:inputOneAutoComplete_input")).click();
        webDriver.findElement(By.id("form:j_idt72:j_idt79:j_idt81:j_idt418:r17c2reg:inputOneAutoComplete_input")).clear();
        webDriver.findElement(By.id("form:j_idt72:j_idt79:j_idt81:j_idt418:r17c2reg:inputOneAutoComplete_input")).sendKeys("cot");
        webDriver.findElement(By.xpath("//div[@id='form:j_idt72:j_idt79:j_idt81:j_idt418:r17c2reg:inputOneAutoComplete_panel']/ul/li")).click();
        webDriver.findElement(By.xpath("//table[@id='form:j_idt72:j_idt79:j_idt81:j_idt418:r19c2reg:inputOneRadio']/tbody/tr/td[3]/div/div[2]/span")).click();
        webDriver.findElement(By.id("form:j_idt72:j_idt79:j_idt81:j_idt418:r19c2reg:inputOneRadio:1")).click();
        webDriver.findElement(By.id("form:j_idt72:j_idt79:j_idt81:j_idt418:r20c2reg:inputOneAutoComplete_input")).click();
        webDriver.findElement(By.id("form:j_idt72:j_idt79:j_idt81:j_idt418:r20c2reg:inputOneAutoComplete_input")).clear();
        webDriver.findElement(By.id("form:j_idt72:j_idt79:j_idt81:j_idt418:r20c2reg:inputOneAutoComplete_input")).sendKeys("fr");
        webDriver.findElement(By.xpath("//div[@id='form:j_idt72:j_idt79:j_idt81:j_idt418:r20c2reg:inputOneAutoComplete_panel']//li[.='French']")).click();
        webDriver.findElement(By.id("form:j_idt72:j_idt79:j_idt81:j_idt418:r18c2reg:inputFile")).click();
        webDriver.findElement(By.id("form:j_idt764:j_idt764Button")).click();
        webDriver.findElement(By.id("form:messageDialogIdOkButton")).click();
        webDriver.findElement(By.id("form:j_idt72:dataTable:1:j_idt169:j_idt169Button")).click();
        webDriver.findElement(By.linkText("Supprimer")).click();
        webDriver.findElement(By.linkText("Lister personne")).click();
        webDriver.findElement(By.id("form:j_idt72:dataTable:1:j_idt165:j_idt165Button")).click();
        webDriver.findElement(By.id("form:j_idt764:j_idt764ButtonWithConfirm")).click();
        webDriver.findElement(By.id("form:j_idt764:j_idt199")).click();
        webDriver.findElement(By.id("form:messageDialogIdOkButton")).click();
        webDriver.findElement(By.linkText("Se deconnecter")).click();
        
	}

}
