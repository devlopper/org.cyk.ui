package org.cyk.ui.api.model.pattern.tree;

import org.cyk.system.root.business.api.Crud;
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
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractDataTreeNodeForm<ENUMERATION extends AbstractDataTreeNode> extends AbstractEnumerationForm<ENUMERATION> {
	private static final long serialVersionUID = 3546404581804831710L;
	
	@Input(readOnly=true,disabled=true) @InputText protected String currentParent;
	@Input @InputChoice @InputOneChoice @InputChoiceAutoComplete @InputOneAutoComplete protected ENUMERATION newParent;
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
		if(identifiable.getIdentifier()!=null){
			this.newParent = tempCurrentParent = (ENUMERATION) getBusiness().findParent(identifiable);
			//this.currentParent = newParent;
			this.currentParent = inject(FormatterBusiness.class).format(newParent);	
		}
		
	}
	
	public Boolean isCurrentParentEqualsNewParent(){
		return (currentParent==null && newParent==null) || currentParent.equals(newParent);
	}
	@Override
	public void write() {
		super.write();
		if(identifiable.getIdentifier()==null)
			identifiable.setParent(newParent);
		else
			identifiable.setNewParent(newParent);
	}
	
	public static final String FIELD_CURRENT_PARENT = "currentParent";
	public static final String FIELD_NEW_PARENT = "newParent";
}