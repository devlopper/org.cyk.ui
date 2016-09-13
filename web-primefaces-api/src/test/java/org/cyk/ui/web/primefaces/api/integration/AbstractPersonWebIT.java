package org.cyk.ui.web.primefaces.api.integration;

import org.cyk.ui.api.model.geography.LocationFormModel;
import org.cyk.ui.api.model.language.LanguageCollectionFormModel;
import org.cyk.ui.web.primefaces.page.party.PersonEditPage;
import org.cyk.ui.web.primefaces.test.automation.Form;

public abstract class AbstractPersonWebIT extends AbstractWebIT {

	private static final long serialVersionUID = 1L;
	
    @Override
	protected void __execute__() {
       super.__execute__();
       
        helper.clickOnMenuItem("PARTY","Lister personne");
        helper.clickCommand("add");
        
        Form form = new Form();
        form.addInputText(PersonEditPage.Form.FIELD_CODE, "pers0011")
        	.addInputText(PersonEditPage.Form.FIELD_NAME,"yao")
        	.addInputText(PersonEditPage.Form.FIELD_LAST_NAMES, "evelyne")
        	.addInputOneRadio(PersonEditPage.Form.FIELD_SEX, 0)
        	.addInputCalendar(PersonEditPage.Form.FIELD_BIRTH_DATE, "01/02/1993")
        	.addInputOneAutoComplete(LocationFormModel.FIELD_LOCALITY, "ab", 1)
        	.addInputOneAutoComplete(PersonEditPage.Form.FIELD_NATIONALITY, "cot", 0)
        	.addInputPersonImage(PersonEditPage.Form.FIELD_IMAGE, Boolean.TRUE)
        	.addInputOneRadio(PersonEditPage.Form.FIELD_BLOOD_GROUP, 1)
        	.addInputOneAutoComplete(LanguageCollectionFormModel.FIELD_LANGUAGE_1, "fr", 0)
        	;
        form.sendKeys();
        form.submit();
        
        showReadFormFromTable(2);
        
        clickEditContextMenu();
        
        form = new Form();
        form.addInputText(PersonEditPage.Form.FIELD_CODE, "PA021")
        	.addInputText(PersonEditPage.Form.FIELD_NAME,"ZOUZOU")
        	.addInputText(PersonEditPage.Form.FIELD_LAST_NAMES, "Ange")
        	.addInputPersonImage(PersonEditPage.Form.FIELD_IMAGE, Boolean.FALSE)
        	;
        form.sendKeys();
        form.submit();
        
        clickDeleteContextMenu();
        form = new Form();
        form.getSubmitCommandable().setConfirmed(Boolean.TRUE);
        form.sendKeys();
        form.submit();
        
	}
       
}
