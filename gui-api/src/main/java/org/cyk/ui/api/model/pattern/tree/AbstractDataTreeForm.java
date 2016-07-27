package org.cyk.ui.api.model.pattern.tree;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.pattern.tree.AbstractDataTree;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeType;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;

@Getter @Setter
public abstract class AbstractDataTreeForm<ENUMERATION extends AbstractDataTree<TYPE>,TYPE extends AbstractDataTreeType> extends AbstractDataTreeNodeForm<ENUMERATION> {
	private static final long serialVersionUID = 3546404581804831710L;
	
	@Input @InputChoice @InputOneChoice @InputOneCombo @NotNull
	protected TYPE type;
	
	public AbstractDataTreeForm() {}
	
	public AbstractDataTreeForm(ENUMERATION enumeration) {
		super(enumeration);
	}
	
	public static final String FIELD_TYPE = "type";
	
	public static class Default extends AbstractDataTreeForm<AbstractDataTree<AbstractDataTreeType>,AbstractDataTreeType> implements Serializable {
		private static final long serialVersionUID = 6167774961363500533L;

	}
	
}