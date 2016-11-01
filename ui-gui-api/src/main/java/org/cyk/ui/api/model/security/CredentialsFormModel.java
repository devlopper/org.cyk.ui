package org.cyk.ui.api.model.security;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.security.Credentials;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputPassword;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.annotation.user.interfaces.Text;
import org.cyk.utility.common.validation.Client;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CredentialsFormModel implements Serializable {

	private static final long serialVersionUID = -465747050467060317L;
 
	@Input @InputText @NotNull private String username;
	
	@Input @InputPassword @NotNull private String password;
	
	@Input(label=@Text(value="field.password.confirmation"))
	@InputPassword
	@NotNull(groups={Client.class})
	private String passwordConfirmation;
	
	public void set(Credentials credentials){
		username = credentials.getUsername();
		password = credentials.getPassword();
	}
	
	public void write(Credentials credentials,Boolean handlePassword){
		credentials.setUsername(username);
		if(Boolean.TRUE.equals(handlePassword))
			credentials.setPassword(password);
	}
	
	public static final String FIELD_USERNAME = "username";
	public static final String FIELD_PASSWORD = "password";
	public static final String FIELD_PASSWORD_CONFIRMATION = "passwordConfirmation";
	
}
