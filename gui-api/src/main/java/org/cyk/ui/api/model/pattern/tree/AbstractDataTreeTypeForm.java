package org.cyk.ui.api.model.pattern.tree;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.pattern.tree.AbstractDataTreeType;

@Getter @Setter
public abstract class AbstractDataTreeTypeForm<ENUMERATION extends AbstractDataTreeType> extends AbstractDataTreeNodeForm<ENUMERATION> implements Serializable {
	private static final long serialVersionUID = 3546404581804831710L;
	
	public AbstractDataTreeTypeForm() {}
	
	public AbstractDataTreeTypeForm(ENUMERATION enumeration) {
		super(enumeration);
	}
	
	public static class Default extends AbstractDataTreeTypeForm<AbstractDataTreeType> implements Serializable {
		private static final long serialVersionUID = 6167774961363500533L;

	}
	
}