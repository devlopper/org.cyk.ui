package org.cyk.ui.api.model.pattern.tree;

import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeNodeBusiness;
import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.ui.api.model.AbstractEnumerationForm;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractDataTreeNodeForm<ENUMERATION extends AbstractDataTreeNode> extends AbstractEnumerationForm<ENUMERATION> {
	private static final long serialVersionUID = 3546404581804831710L;
	
	//@Input @InputChoice @InputOneChoice @InputOneCombo //TODO not working when enabled
	protected ENUMERATION parent;
	
	public AbstractDataTreeNodeForm() {}
	
	public AbstractDataTreeNodeForm(ENUMERATION enumeration) {
		super(enumeration);
	}
	
	protected AbstractDataTreeNodeBusiness<ENUMERATION> getBusiness(){
		return (AbstractDataTreeNodeBusiness<ENUMERATION>) BusinessInterfaceLocator.getInstance().injectTypedByObject(identifiable);
	}
	
	@Override
	public void read() {
		super.read();
		//this.parent = getBusiness().findParent(identifiable);
	}
	
	@Override
	public void write() {
		super.write();
		//identifiable.setParent(parent);
	}
	
	public static final String FIELD_PARENT = "parent";
}