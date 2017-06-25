package org.cyk.ui.web.primefaces.page.pattern.tree;

import java.io.Serializable;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.ui.api.model.pattern.tree.AbstractDataTreeNodeForm;
import org.cyk.ui.web.primefaces.page.AbstractIdentifiablePagesConfiguration;
import org.cyk.utility.common.Constant.Action;

public abstract class AbstractDataTreeNodePagesConfiguration<TYPE extends AbstractDataTreeNode> extends AbstractIdentifiablePagesConfiguration<TYPE> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected String[] getFieldNames(Action action) {
		return ArrayUtils.addAll(super.getFieldNames(action),AbstractDataTreeNodeForm.FIELD_PARENT);
	}
	
}
