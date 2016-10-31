package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.cyk.system.root.business.api.security.UserAccountBusiness;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudManyPage;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class UserAccountListPage extends AbstractCrudManyPage<UserAccount> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Inject private UserAccountBusiness userAccountBusiness;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		contextualMenu = userSession.getSecurityMenu();
		rowAdapter.setOpenable(Boolean.TRUE);
		rowAdapter.setUpdatable(Boolean.TRUE);
		rowAdapter.setDeletable(Boolean.TRUE);
		table.setShowHeader(Boolean.TRUE);
		table.setShowToolBar(Boolean.TRUE);
	}
	
	

}
