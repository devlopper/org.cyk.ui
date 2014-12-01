package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.security.Credentials;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputPassword;
import org.cyk.utility.common.annotation.user.interfaces.Text;
import org.cyk.utility.common.validation.Client;

@Getter @Setter
public class CredentialsInputs implements Serializable {

	private static final long serialVersionUID = -465747050467060317L;
 
	@IncludeInputs
	private Credentials credentials = new Credentials();
	
	@Input(label=@Text(value="field.password.confirmation"))
	@InputPassword
	@NotNull(groups={Client.class})
	private String passwordConfirmation;
	
}
