package org.cyk.ui.web.primefaces.page.userinterface;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.userinterface.UserInterfaceCommand;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuNode;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuNodeType;
import org.cyk.ui.api.model.pattern.tree.AbstractDataTreeForm;
import org.cyk.ui.web.primefaces.page.geography.AbstractDataTreeNodeEditPage;
import org.cyk.utility.common.annotation.FieldOverride;
import org.cyk.utility.common.annotation.FieldOverrides;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputBooleanButton;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputChoiceAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputOneAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class UserInterfaceMenuNodeEditPage extends AbstractDataTreeNodeEditPage<UserInterfaceMenuNode> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;	
	
	@Getter @Setter 
	@FieldOverrides(value = {
			@FieldOverride(name=AbstractDataTreeForm.FIELD_PARENT,type=UserInterfaceMenuNode.class)
			,@FieldOverride(name=AbstractDataTreeForm.FIELD_TYPE,type=UserInterfaceMenuNodeType.class)
			})
	public static class Form extends AbstractDataTreeForm<UserInterfaceMenuNode,UserInterfaceMenuNodeType> {
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputChoice @InputChoiceAutoComplete @InputOneChoice @InputOneAutoComplete private UserInterfaceCommand command;
		@Input @InputBooleanButton private Boolean automaticallyCreateCommand;
		
		public static final String FIELD_COMMAND = "command";
		public static final String FIELD_AUTOMATICALLY_CREATE_COMMAND = "automaticallyCreateCommand";
	
	}
	
}
