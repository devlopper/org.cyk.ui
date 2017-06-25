package org.cyk.ui.web.primefaces.page.userinterface;

import java.io.Serializable;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.model.userinterface.UserInterfaceMenu;
import org.cyk.ui.web.primefaces.page.AbstractCollectionEditPage;
import org.cyk.ui.web.primefaces.page.AbstractIdentifiablePagesConfiguration;
import org.cyk.utility.common.Constant.Action;

public class UserInterfaceMenuPagesConfiguration extends AbstractIdentifiablePagesConfiguration<UserInterfaceMenu> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected String[] getFieldNames(Action action) {
		return ArrayUtils.addAll(super.getFieldNames(action),AbstractCollectionEditPage.AbstractForm.FIELD_ONE_ITEM_MASTER_SELECTED);
	}
	
}
