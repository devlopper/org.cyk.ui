package org.cyk.ui.api.model.pattern.tree;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.FormatterBusiness;
import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeNodeBusiness;
import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.ui.api.model.AbstractEnumerationForm;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputChoiceAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputOneAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;

@Getter @Setter
public abstract class AbstractDataTreeNodeForm<ENUMERATION extends AbstractDataTreeNode> extends AbstractEnumerationForm<ENUMERATION> {
	private static final long serialVersionUID = 3546404581804831710L;
	
	/*@Input(readOnly=true,disabled=true) @InputText*/ protected String currentParent;
	@Input @InputChoice @InputOneChoice @InputChoiceAutoComplete @InputOneAutoComplete protected ENUMERATION parent;
	protected ENUMERATION tempCurrentParent;
	
	public AbstractDataTreeNodeForm() {}
	
	public AbstractDataTreeNodeForm(ENUMERATION enumeration) {
		super(enumeration);
	}
	
	protected AbstractDataTreeNodeBusiness<ENUMERATION> getBusiness(){
		return (AbstractDataTreeNodeBusiness<ENUMERATION>) BusinessInterfaceLocator.getInstance().injectTypedByObject(identifiable);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void read() {
		super.read();
		this.parent = tempCurrentParent = (ENUMERATION)  identifiable.getParent(); //getBusiness().findParent(identifiable);
		if(parent!=null)
			this.currentParent = inject(FormatterBusiness.class).format(parent);	
	}
	
	public Boolean isCurrentParentEqualsNewParent(){
		return (currentParent==null && parent==null) || currentParent.equals(parent);
	}
	@Override
	public void write() {
		super.write();
		if(identifiable.getIdentifier()==null)
			identifiable.setParent(parent);
		else
			identifiable.setNewParent(parent);
	}
	
	public static final String FIELD_CURRENT_PARENT = "currentParent";
	public static final String FIELD_PARENT = "parent";
}