package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.utility.common.annotation.user.interfaces.Binding;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputManyCheck;
import org.cyk.utility.common.annotation.user.interfaces.InputManyChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;

@Getter @Setter
public class UserAccountFormModel extends AbstractFormModel<UserAccount> implements Serializable {

	private static final long serialVersionUID = -392868128587378419L;

	@Input @InputChoice @InputOneChoice @InputOneCombo
	@Binding(field="user")
	private Person user;
	
	@IncludeInputs
	private CredentialsInputs credentialsInputs = new CredentialsInputs();
	
	@Input @InputChoice @InputManyChoice @InputManyCheck
	private List<Role> roles = new ArrayList<>();
		
	@Override
	public void write() {
		super.write();
		//identifiable.setUser(user);
		//identifiable.setCreationDate(new Date());
		identifiable.setCredentials(credentialsInputs.getCredentials());
		identifiable.getRoles().addAll(roles);
		//debug(identifiable);
	}
	
	@Override
	public void read() {
		super.read();
		//debug(identifiable);
	}
	
}
