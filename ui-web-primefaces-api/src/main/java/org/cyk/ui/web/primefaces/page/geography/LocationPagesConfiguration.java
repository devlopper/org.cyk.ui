package org.cyk.ui.web.primefaces.page.geography;

import java.io.Serializable;

import org.cyk.system.root.model.geography.Location;
import org.cyk.utility.common.Constant.Action;

public class LocationPagesConfiguration extends AbstractContactPagesConfiguration<Location> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected String[] getRequiredFieldNames(Action action) {
		return new String[]{LocationEditPage.Form.FIELD_TYPE,LocationEditPage.Form.FIELD_LOCALITY};
	}
	
	@Override
	protected String[] getFieldNames(Action action) {
		return new String[]{LocationEditPage.Form.FIELD_COLLECTION,LocationEditPage.Form.FIELD_OTHER_DETAILS};
	}
}
