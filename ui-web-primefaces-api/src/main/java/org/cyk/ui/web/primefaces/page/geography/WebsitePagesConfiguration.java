package org.cyk.ui.web.primefaces.page.geography;

import java.io.Serializable;

import org.cyk.system.root.model.geography.Website;
import org.cyk.utility.common.Constant.Action;

public class WebsitePagesConfiguration extends AbstractContactPagesConfiguration<Website> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	protected String[] getRequiredFieldNames(Action action) {
		return new String[]{WebsiteEditPage.Form.FIELD_UNIFORM_RESOURCE_LOCATOR};
	}
	
	@Override
	protected String[] getFieldNames(Action action) {
		return new String[]{WebsiteEditPage.Form.FIELD_COLLECTION};
	}
}
