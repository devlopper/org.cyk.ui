package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.security.UserAccount;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage;

@Getter @Setter
public abstract class AbstractUserAccountConsultPage extends AbstractConsultPage<UserAccount> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
}
