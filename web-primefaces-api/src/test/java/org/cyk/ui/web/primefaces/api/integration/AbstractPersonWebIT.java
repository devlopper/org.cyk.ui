package org.cyk.ui.web.primefaces.api.integration;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.cyk.ui.web.api.test.automation.SeleniumHelper;
import org.cyk.utility.common.generator.RandomDataProvider;
import org.cyk.utility.common.generator.RandomDataProvider.RandomFile;
import org.cyk.utility.test.integration.ui.web.AbstractIntegrationWebTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AbstractPersonWebIT extends AbstractWebIT {

	private static final long serialVersionUID = 1L;
	
    @Override
	protected void __execute__() {
       super.__execute__();
       
        helper.clickOnMenuItem("PARTY","Lister personne");
        
        getDriver().findElement(By.xpath("/html/body/div[3]/div[2]/form/div[1]/div/div[2]/div[1]/div/div[1]/button")).click();
        
        RandomFile randomFile1 = RandomDataProvider.getInstance().getMale().photo();
        File file1 = randomFile1.createTemporaryFile();
        fillForm(Boolean.FALSE,"pers0011","yao","evelyne",file1.getPath());        
        submitForm();
        showReadFormFromTable(2);
        
        pause(3 * 1000l);
        /*
        clickEditContextMenu();
        RandomFile randomFile2 = RandomDataProvider.getInstance().getMale().photo();
        File file2 = randomFile2.createTemporaryFile();
        fillForm(Boolean.TRUE,"PA021","Zouzou","Ange",file2.getPath());
        submitForm();
        pause(3 * 1000l);
        
        clickDeleteContextMenu();
        clickFormSubmitButton();
        clickFormOperationConfirmYesButton();
        clickFormOperationResultOkButton(Boolean.FALSE);
        */
	}
    
    protected void fillForm(Boolean hasImage,String code,String firstname,String lastnames,String imagePath){
        helper.sendKeys("inputtext_code_", code);
        helper.sendKeys("inputtext_nom_", firstname);
        helper.sendKeys("inputtext_prenoms", lastnames);
        /*
        getDriver().findElement(By.xpath("/html/body/div[3]/div[2]/form/div[1]/div/table[2]/tbody/tr[14]/td[2]/table/tbody/tr/td[1]/table/tbody/tr/td[1]/div/div[2]/span")).click();
        
        getDriver().findElement(By.xpath("/html/body/div[3]/div[2]/form/div[1]/div/table[2]/tbody/tr[15]/td[2]/table/tbody/tr/td[1]/span/input")).click();
        getDriver().findElement(By.linkText("5")).click();
        
        sendAutocompleteKeys("/html/body/div[3]/div[2]/form/div[1]/div/table[2]/tbody/tr[16]/td[2]/table/tbody/tr/td[1]/span/input[1]", "ab"
        		,"/html/body/div[10]/ul/li[2]");
       
        sendAutocompleteKeys("/html/body/div[3]/div[2]/form/div[1]/div/table[2]/tbody/tr[17]/td[2]/table/tbody/tr/td[1]/span/input[1]", "cot"
        		,"/html/body/div[11]/ul/li");
        
        helper.sendKeys("/html/body/div[3]/div[2]/form/div[1]/div/table[2]/tbody/tr[18]/td[2]/table/tbody/tr[2]/td[1]/table/tbody/tr[1]/td/input"
        		, imagePath);
        
        getDriver().findElement(By.xpath("/html/body/div[3]/div[2]/form/div[1]/div/table[2]/tbody/tr[19]/td[2]/table/tbody/tr/td[1]/table/tbody/tr/td[5]/div/div[2]/span")).click();
        
        sendAutocompleteKeys("/html/body/div[3]/div[2]/form/div[1]/div/table[2]/tbody/tr[20]/td[2]/table/tbody/tr/td[1]/span/input[1]", "fr"
        		,"/html/body/div["+(Boolean.TRUE.equals(hasImage) ? 14 : 12)+"]/ul/li[1]");
        */
    }
    
}
