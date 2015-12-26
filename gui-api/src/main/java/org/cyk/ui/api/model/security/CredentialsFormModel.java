package org.cyk.ui.api.model.security;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.security.Credentials;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs.Layout;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputPassword;
import org.cyk.utility.common.annotation.user.interfaces.Text;
import org.cyk.utility.common.validation.Client;

@Getter @Setter
public class CredentialsFormModel implements Serializable {

	private static final long serialVersionUID = -465747050467060317L;
 
	@IncludeInputs(layout=Layout.VERTICAL) private Credentials credentials = new Credentials();
	
	@Input(label=@Text(value="field.password.confirmation"))
	@InputPassword
	@NotNull(groups={Client.class})
	private String passwordConfirmation;
	
	public static final String FIELD_PASSWORD_CONFIRMATION = "passwordConfirmation";
	
}
