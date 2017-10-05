package org.cyk.ui.web.primefaces.page.geography;

import java.io.Serializable;

import org.cyk.system.root.model.geography.ElectronicMailAddress;
import org.cyk.utility.common.Constant.Action;

public class ElectronicMailAddressPagesConfiguration extends AbstractContactPagesConfiguration<ElectronicMailAddress> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected String[] getRequiredFieldNames(Action action) {
		return new String[]{ElectronicMailAddressEditPage.Form.FIELD_ADDRESS};
	}
	
	@Override
	protected String[] getFieldNames(Action action) {
		return new String[]{ElectronicMailAddressEditPage.Form.FIELD_COLLECTION};
	}
	
}
