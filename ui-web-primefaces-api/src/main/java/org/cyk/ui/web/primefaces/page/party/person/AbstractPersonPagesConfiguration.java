package org.cyk.ui.web.primefaces.page.party.person;

import java.io.Serializable;

import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.web.primefaces.page.AbstractIdentifiablePagesConfiguration;
import org.cyk.utility.common.Constant.Action;

public class AbstractPersonPagesConfiguration extends AbstractIdentifiablePagesConfiguration<Person> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*@Override
	protected String[] getRequiredFieldNames(Action action) {
		return new String[]{PersonEditPage.Form.FIELD_NAME};
	}
	
	@Override
	protected String[] getFieldNames(Action action) {
		if(Action.READ.equals(action))
			return new String[]{PersonEditPage.Form.FIELD_CODE,PersonEditPage.Form.FIELD_NAME,PersonEditPage.Form.FIELD_LAST_NAMES};
		
		return new String[]{PersonEditPage.Form.FIELD_CODE,PersonEditPage.Form.FIELD_IMAGE,PersonEditPage.Form.FIELD_LAST_NAMES
				,PersonEditPage.Form.FIELD_SEX,PersonEditPage.Form.FIELD_BIRTH_DATE,PersonEditPage.Form.FIELD_BIRTH_LOCATION,PersonEditPage.Form.FIELD_NATIONALITY
				,PersonEditPage.Form.FIELD_MARITAL_STATUS,PersonEditPage.Form.FIELD_TITLE,PersonEditPage.Form.FIELD_SIGNATURE_SPECIMEN
				,PersonEditPage.Form.FIELD_OTHER_DETAILS};
	}*/
}
