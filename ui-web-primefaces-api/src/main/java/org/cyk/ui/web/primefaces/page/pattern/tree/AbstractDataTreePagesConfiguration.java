package org.cyk.ui.web.primefaces.page.pattern.tree;

import java.io.Serializable;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.ui.api.model.pattern.tree.AbstractDataTreeForm;
import org.cyk.utility.common.Constant.Action;

public abstract class AbstractDataTreePagesConfiguration<TYPE extends AbstractDataTreeNode> extends AbstractDataTreeNodePagesConfiguration<TYPE> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected String[] getFieldNames(Action action) {
		return ArrayUtils.addAll(super.getFieldNames(action),AbstractDataTreeForm.FIELD_TYPE);
	}

}
