package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.model.security.UserAccountUserInterfaceMenu;
import org.cyk.system.root.model.userinterface.UserInterfaceMenu;
import org.cyk.ui.api.model.AbstractBusinessIdentifiedEditFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class UserAccountUserInterfaceMenuEditPage extends AbstractCrudOnePage<UserAccountUserInterfaceMenu> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	/**/
	
	@Getter @Setter
	public static class Form extends AbstractBusinessIdentifiedEditFormModel<UserAccountUserInterfaceMenu> implements Serializable {

		private static final long serialVersionUID = 2646571878912106597L;
		
		@Input @InputChoice /*@InputChoiceAutoComplete*/ @InputOneChoice /*@InputOneAutoComplete*/ @InputOneCombo private UserAccount userAccount;
		@Input @InputChoice /*@InputChoiceAutoComplete*/ @InputOneChoice /*@InputOneAutoComplete*/ @InputOneCombo private UserInterfaceMenu userInterfaceMenu;
		
		public static final String FIELD_USER_ACCOUNT = "userAccount";
		public static final String FIELD_USER_INTERFACE_MENU = "userInterfaceMenu";
		
	}

}
