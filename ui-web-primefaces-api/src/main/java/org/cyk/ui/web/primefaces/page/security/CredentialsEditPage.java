package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.model.security.Software;
import org.cyk.ui.api.model.AbstractBusinessIdentifiedEditFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputChoiceAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputOneAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputPassword;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class CredentialsEditPage extends AbstractCrudOnePage<Credentials> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	/**/
	
	@Getter @Setter
	public static class Form extends AbstractBusinessIdentifiedEditFormModel<Credentials> implements Serializable {

		private static final long serialVersionUID = 2646571878912106597L;
		
		@Input @InputChoice @InputChoiceAutoComplete @InputOneChoice @InputOneAutoComplete @NotNull private Software software;
		@Input @InputText @NotNull private String username;
		@Input @InputPassword @NotNull private String password;
		
		public static final String FIELD_SOFTWARE = "software";
		public static final String FIELD_USERNAME = "username";
		public static final String FIELD_PASSWORD = "password";
	}

}
