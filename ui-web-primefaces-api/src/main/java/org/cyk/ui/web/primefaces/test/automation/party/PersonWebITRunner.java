package org.cyk.ui.web.primefaces.test.automation.party;

import java.io.Serializable;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.web.primefaces.test.automation.Form;
import org.cyk.ui.web.primefaces.test.automation.IdentifiableWebITRunner;

public class PersonWebITRunner extends IdentifiableWebITRunner<Person> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	public String[] getListMenuItemPath() {
		return new String[]{"commandable_model_entity_person_","commandable_list_person_"};
	}
	@Override
	public String getCode(Crud crud) {
		switch(crud){
		case CREATE:return "pers0011";
		case READ:return "pers0011";
		case UPDATE:return "PA021";
		case DELETE:return "PA021";
		}
		return null;
	}
	@Override
	public void fillForm(Form form,Crud crud) {
		/*switch(crud){
		case CREATE:
			form.addInputText(PersonEditPage.Form.FIELD_NAME,"yao")
	        	.addInputText(PersonEditPage.Form.FIELD_LAST_NAMES, "evelyne")
	        	.addInputOneRadio(PersonEditPage.Form.FIELD_SEX, 0)
	        	.addInputCalendar(PersonEditPage.Form.FIELD_BIRTH_DATE, "01/02/1993")
	        	.addInputOneAutoComplete(LocationFormModel.FIELD_LOCALITY, "ab", 1)
	        	.addInputOneAutoComplete(PersonEditPage.Form.FIELD_NATIONALITY, "cot", 0)
	        	.addInputPersonImage(PersonEditPage.Form.FIELD_IMAGE, Boolean.TRUE)
	        	.addInputOneRadio(PersonEditPage.Form.FIELD_BLOOD_GROUP, 1)
	        	.addInputOneAutoComplete(LanguageCollectionFormModel.FIELD_LANGUAGE_1, "fr", 0)
		        ;
			break;
		case READ:
			break;
		case UPDATE:
			form.addInputText(PersonEditPage.Form.FIELD_NAME,"ZOUZOU")
        		.addInputText(PersonEditPage.Form.FIELD_LAST_NAMES, "Ange")
        		.addInputPersonImage(PersonEditPage.Form.FIELD_IMAGE, Boolean.FALSE)
		        ;
			break;
		case DELETE:
			break;
		}*/
	}

	
}
