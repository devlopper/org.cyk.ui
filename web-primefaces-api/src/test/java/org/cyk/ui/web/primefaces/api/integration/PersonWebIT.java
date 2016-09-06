package org.cyk.ui.web.primefaces.api.integration;

import java.util.concurrent.TimeUnit;

import org.cyk.utility.test.integration.ui.web.AbstractIntegrationWebTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PersonWebIT extends AbstractIntegrationWebTest {

	private static final long serialVersionUID = 1L;

    @Override
	protected void __execute__(WebDriver webDriver) {
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
	}

}
