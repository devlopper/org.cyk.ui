package org.cyk.ui.web.primefaces.api.integration;

import org.cyk.system.root.business.api.Crud;
import org.cyk.ui.api.model.geography.LocationFormModel;
import org.cyk.ui.api.model.language.LanguageCollectionFormModel;
import org.cyk.ui.web.primefaces.page.party.PersonEditPage;
import org.cyk.ui.web.primefaces.test.automation.Form;

public abstract class AbstractPersonWebIT extends AbstractEntityWebIT {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected String[] getListMenuItemPath() {
		return new String[]{"PARTY","Lister personne"};
	}
	
	@Override
	protected Form getForm(Crud crud) {
		switch(crud){
		case CREATE:
			return new Form().addInputText(PersonEditPage.Form.FIELD_CODE, "pers0011")
		        	.addInputText(PersonEditPage.Form.FIELD_NAME,"yao")
		        	.addInputText(PersonEditPage.Form.FIELD_LAST_NAMES, "evelyne")
		        	.addInputOneRadio(PersonEditPage.Form.FIELD_SEX, 0)
		        	.addInputCalendar(PersonEditPage.Form.FIELD_BIRTH_DATE, "01/02/1993")
		        	.addInputOneAutoComplete(LocationFormModel.FIELD_LOCALITY, "ab", 1)
		        	.addInputOneAutoComplete(PersonEditPage.Form.FIELD_NATIONALITY, "cot", 0)
		        	.addInputPersonImage(PersonEditPage.Form.FIELD_IMAGE, Boolean.TRUE)
		        	.addInputOneRadio(PersonEditPage.Form.FIELD_BLOOD_GROUP, 1)
		        	.addInputOneAutoComplete(LanguageCollectionFormModel.FIELD_LANGUAGE_1, "fr", 0);
		case READ:return null;
		case UPDATE:
			return new Form().addInputText(PersonEditPage.Form.FIELD_CODE, "PA021")
		        	.addInputText(PersonEditPage.Form.FIELD_NAME,"ZOUZOU")
		        	.addInputText(PersonEditPage.Form.FIELD_LAST_NAMES, "Ange")
		        	.addInputPersonImage(PersonEditPage.Form.FIELD_IMAGE, Boolean.FALSE);
		case DELETE:return new Form();
		}
		return null;
	}
	
	@Override
	protected Integer getRecordIndex() {
		return 2;
	}
	   
}
