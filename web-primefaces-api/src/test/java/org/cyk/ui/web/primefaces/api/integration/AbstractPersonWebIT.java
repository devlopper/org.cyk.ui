package org.cyk.ui.web.primefaces.api.integration;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.cyk.ui.api.model.geography.LocationFormModel;
import org.cyk.ui.api.model.language.LanguageCollectionFormModel;
import org.cyk.ui.web.primefaces.page.party.PersonEditPage;
import org.cyk.ui.web.primefaces.test.automation.SeleniumHelper;
import org.cyk.utility.common.generator.RandomDataProvider;
import org.cyk.utility.common.generator.RandomDataProvider.RandomFile;
import org.cyk.utility.test.integration.ui.web.AbstractIntegrationWebTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AbstractPersonWebIT extends AbstractWebIT {

	private static final long serialVersionUID = 1L;
	
    @Override
	protected void __execute__() {
       super.__execute__();
       
        helper.clickOnMenuItem("PARTY","Lister personne");
        helper.clickCommand("add");
        
        RandomFile randomFile1 = RandomDataProvider.getInstance().getMale().photo();
        File file1 = randomFile1.createTemporaryFile();
        fillForm("pers0011","yao","evelyne",0,"01/02/1993",new Object[]{"ab", 1},file1.getPath());        
        /*submitForm();
        showReadFormFromTable(2);
        
        pause(3 * 1000l);
        */
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
    
    protected void fillForm(String code,String firstname,String lastnames,Integer sex,String birthDate,Object[] localities,String imagePath){
        helper.sendKeysOnInput(PersonEditPage.Form.FIELD_CODE, code);
        helper.sendKeysOnInput(PersonEditPage.Form.FIELD_NAME, firstname);
        helper.sendKeysOnInput(PersonEditPage.Form.FIELD_LAST_NAMES, lastnames);
        helper.clickOnRadioInput(PersonEditPage.Form.FIELD_SEX, sex);
        helper.sendKeysOnInput(PersonEditPage.Form.FIELD_BIRTH_DATE, birthDate);
        helper.sendKeysOnAutocompleteInput(LocationFormModel.FIELD_LOCALITY, (String)localities[0],(Integer)localities[1]);
        helper.sendKeysOnAutocompleteInput(PersonEditPage.Form.FIELD_NATIONALITY, "cot", 0);
        helper.sendKeysOnInput(PersonEditPage.Form.FIELD_IMAGE, imagePath);
        helper.clickOnRadioInput(PersonEditPage.Form.FIELD_BLOOD_GROUP, 2);
        helper.sendKeysOnAutocompleteInput(LanguageCollectionFormModel.FIELD_LANGUAGE_1, "fr", 0);
    }
       
}
