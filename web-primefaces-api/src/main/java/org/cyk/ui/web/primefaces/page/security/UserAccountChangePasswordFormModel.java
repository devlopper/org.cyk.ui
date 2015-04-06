package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.security.UserAccount;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputPassword;
import org.cyk.utility.common.validation.Client;

@Getter @Setter
public class UserAccountChangePasswordFormModel extends AbstractFormModel<UserAccount> implements Serializable {

	private static final long serialVersionUID = -6718318186450819203L;

	@Input
	@InputPassword
	@NotNull(groups={Client.class})
	private String currentPassword;
	
	@Input
	@InputPassword
	@NotNull(groups={Client.class})
	private String newPassword;
	
	@Input
	@InputPassword
	@NotNull(groups={Client.class})
	private String newPasswordConfirmation;
	
	@Override
	public void write() {
		super.write();
		identifiable.getCredentials().setPassword(newPassword);
	}
	
}
