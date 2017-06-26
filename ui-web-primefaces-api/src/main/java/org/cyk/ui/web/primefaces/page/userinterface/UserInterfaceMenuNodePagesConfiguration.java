package org.cyk.ui.web.primefaces.page.userinterface;

import java.io.Serializable;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuNode;
import org.cyk.ui.web.primefaces.page.pattern.tree.AbstractDataTreePagesConfiguration;
import org.cyk.utility.common.Constant.Action;

public class UserInterfaceMenuNodePagesConfiguration extends AbstractDataTreePagesConfiguration<UserInterfaceMenuNode> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected String[] getFieldNames(Action action) {
		return ArrayUtils.addAll(super.getFieldNames(action),UserInterfaceMenuNodeEditPage.Form.FIELD_AUTOMATICALLY_CREATE_COMMAND);
	}
	
}
