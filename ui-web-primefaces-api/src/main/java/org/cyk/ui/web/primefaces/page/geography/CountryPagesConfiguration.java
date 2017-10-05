package org.cyk.ui.web.primefaces.page.geography;

import java.io.Serializable;

import org.cyk.system.root.model.geography.Country;
import org.cyk.ui.web.primefaces.page.AbstractIdentifiablePagesConfiguration;
import org.cyk.utility.common.Constant.Action;

public class CountryPagesConfiguration extends AbstractIdentifiablePagesConfiguration<Country> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected String[] getRequiredFieldNames(Action action) {
		return new String[]{CountryEditPage.Form.FIELD_NAME};
	}
	
	@Override
	protected String[] getFieldNames(Action action) {
		return new String[]{CountryEditPage.Form.FIELD_CODE,CountryEditPage.Form.FIELD_CONTINENT
				,CountryEditPage.Form.FIELD_PHONE_NUMBER_CODE,CountryEditPage.Form.FIELD_PHONE_NUMBER_FORMAT};
	}
}
