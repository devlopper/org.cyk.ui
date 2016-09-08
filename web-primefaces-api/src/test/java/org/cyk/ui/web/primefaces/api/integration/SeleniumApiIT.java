package org.cyk.ui.web.primefaces.api.integration;

import java.util.concurrent.TimeUnit;

import org.cyk.utility.test.integration.ui.web.AbstractIntegrationWebTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SeleniumApiIT extends AbstractIntegrationWebTest {

	private static final long serialVersionUID = 1L;

    @Override
	protected void __execute__(WebDriver webDriver) {
    	webDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        webDriver.get("http://localhost:8080/gui-primefaces/private/administrator.jsf");
        System.out.println("SeleniumApiIT.__execute__() : ");
        //debug(webDriver.findElement(By.xpath("[@class^='InputText_nom_d_utilisateur_']")));
        //System.out.println(webDriver.findElements(By.xpath("//input[class='ui-inputtext']")).size());
        //debug(webDriver.findElement(By.xpath("input[id*='form:j_idt77:j_idt84:j_idt86:j_idt423:r3c2reg:inputText']")));
        System.out.println(webDriver.findElements(By.cssSelector("[class*='InputPassword_mot_de_passe_']")).size());
	}

}
