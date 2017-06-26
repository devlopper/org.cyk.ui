package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.RoleUserInterfaceMenu;
import org.cyk.system.root.model.userinterface.UserInterfaceMenu;
import org.cyk.ui.api.model.AbstractBusinessIdentifiedEditFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputChoiceAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputOneAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class RoleUserInterfaceMenuEditPage extends AbstractCrudOnePage<RoleUserInterfaceMenu> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Override
	protected Crud crudFromRequestParameter() {
		return Crud.UPDATE;
	}
	
	/**/
	
	@Getter @Setter
	public static class Form extends AbstractBusinessIdentifiedEditFormModel<RoleUserInterfaceMenu> implements Serializable {

		private static final long serialVersionUID = 2646571878912106597L;
		
		@Input @InputChoice @InputChoiceAutoComplete @InputOneChoice @InputOneAutoComplete private Role role;
		@Input @InputChoice @InputChoiceAutoComplete @InputOneChoice @InputOneAutoComplete private UserInterfaceMenu userInterfaceMenu;
		
		public static final String FIELD_ROLE = "role";
		public static final String FIELD_USER_INTERFACE_MENU = "userInterfaceMenu";
		
	}

}
