package org.cyk.ui.web.primefaces.api.integration;

import org.cyk.utility.common.CommonUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class SeleniumApiIT extends AbstractWebIT {

	private static final long serialVersionUID = 1L;

	public SeleniumApiIT() {
		helper.setImplicitlyWaitNumberOfMillisecond(1000 * 5l);
	}
	
    @Override
	protected void __execute__() {
    	super.__execute__();
    	//CommonUtils.getInstance().pause(3000l);
    	helper.goToPage("party/person/list","clazz=Person");
    	//CommonUtils.getInstance().pause(3000l);
    	//pause(1000 * 3l);
    	//getDriver().findElement(By.xpath("/html/body/div[3]/div[2]/form/div[1]/div/div[2]/div[2]/table/tbody/tr[2]/td[5]/table/tbody/tr/td[1]/button")).click();
    	//helper.waitForVisibilityOf(60000l, "identifiable_person_pers0214");
    	//helper.waitForVisibilityOf(60000l, "identifiable_person_pers0214","open");
    	
    	//wait.until(ExpectedConditions.elementToBeClickable(By.id("form:j_idt72:dataTable:1:j_idt164:j_idt164Button")));
    	By by = By.xpath("//button[contains(@class,'commandable_command_open_')]"); //By.cssSelector("[class*='commandable_command_open_']");
    	
    	new WebDriverWait(getDriver(),5).until(ExpectedConditions.elementToBeClickable(by));
    	debug(getDriver().findElement(by));
    	helper.getElementByClassContains("identifiable_person_pers0214","open").click();
    		//.findElement(By.xpath("td[5]/table/tbody/tr/td[1]/button")).click();
    	//CommonUtils.getInstance().pause(3000l);
    	//debug(webDriver.findElement(By.xpath("[@class^='InputText_nom_d_utilisateur_']")));
        //System.out.println(webDriver.findElements(By.xpath("//input[class='ui-inputtext']")).size());
        //debug(webDriver.findElement(By.xpath("input[id*='form:j_idt77:j_idt84:j_idt86:j_idt423:r3c2reg:inputText']")));
        //System.out.println(getDriver().findElements(By.cssSelector("[class*='InputPassword_mot_de_passe_']")).size());
        //System.out.println(SeleniumHelper.getInstance().getElementByClassContains("commandable_model_entity_person_"));
        //System.out.println(SeleniumHelper.getInstance().getElementByClassContains("commandable_list_person_"));
        //SeleniumHelper.getInstance().getElementByClassContains("commandable_list_person_").click();
	}
    
    
}
