package org.cyk.ui.api.model.pattern.tree;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputNumber;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter
public class NestedSetNodeEditFormModel extends AbstractFormModel<NestedSetNode> implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;

	@Input(disabled=true,readOnly=true) @InputText private String identifier;
	@Input(disabled=true,readOnly=true) @InputText private String name;
	
	@Input @InputChoice @InputOneChoice @InputOneCombo private NestedSet set;
	@Input @InputChoice @InputOneChoice @InputOneCombo private NestedSetNode parent;
	
	@Input @InputNumber private Integer leftIndex;
	@Input @InputNumber private Integer rightIndex;
	
	@Input(disabled=true,readOnly=true) @InputText private String link;
	
	@Override
	public void write() {
		super.write();
		
		//identifiable.setSet(rootBusinessLayer.getNestedSetDao().read(setIdentifier));
		//identifiable.setParent(rootBusinessLayer.getNestedSetNodeDao().read(parentIdentifier));
	}
	
	@Override
	public void read() {
		super.read();
		if(identifiable.getIdentifier()==null)
			;
		else
			identifier = identifiable.getIdentifier().toString();
		name = identifiable.getName();
		//setIdentifier = identifiable.getSet().getIdentifier();
		//parentIdentifier = identifiable.getParent().getIdentifier();
	}
	
	public static final String FIELD_SET = "set";
	public static final String FIELD_PARENT = "parent";
	
	public static final String FIELD_LEFT_INDEX = "leftIndex";
	public static final String FIELD_RIGHT_INDEX = "rightIndex";
	
}
