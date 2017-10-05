package org.cyk.ui.web.primefaces.page.geography;

import java.io.Serializable;

import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.utility.common.Constant.Action;

public class PhoneNumberPagesConfiguration extends AbstractContactPagesConfiguration<PhoneNumber> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected String[] getRequiredFieldNames(Action action) {
		return new String[]{PhoneNumberEditPage.Form.FIELD_COUNTRY,PhoneNumberEditPage.Form.FIELD_NUMBER};
	}
	
	@Override
	protected String[] getFieldNames(Action action) {
		return new String[]{PhoneNumberEditPage.Form.FIELD_COLLECTION,PhoneNumberEditPage.Form.FIELD_TYPE};
	}
	
}
