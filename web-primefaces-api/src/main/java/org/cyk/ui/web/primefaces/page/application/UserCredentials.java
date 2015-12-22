package org.cyk.ui.web.primefaces.page.application;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.model.security.CredentialsFormModel;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.validation.Client;

@Getter @Setter
public class UserCredentials implements Serializable {

	private static final long serialVersionUID = -465747050467060317L;

	@Input
	@InputText
	@NotNull(groups={Client.class})
	private String name;
	
	@IncludeInputs
	private CredentialsFormModel credentialsInputs = new CredentialsFormModel();
	
}
