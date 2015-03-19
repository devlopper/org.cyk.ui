package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.security.UserAccountBusiness;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.model.security.UserAccountSearchCriteria;
import org.cyk.ui.web.primefaces.page.AbstractBusinessQueryPage;

@Named @ViewScoped @Getter @Setter
public class UserAccountsPage extends AbstractBusinessQueryPage<UserAccount, UserAccountSearchFormModel, UserAccountSearchResultFormModel> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Inject private UserAccountBusiness userAccountBusiness;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		contextualMenu = userSession.getSecurityMenu();
		//contextualMenu.setRenderType(RenderType.PANEL);
		table.setShowHeader(Boolean.FALSE);
		table.setShowFooter(Boolean.FALSE);
	}
	
	@Override
	protected Boolean autoLoad() {
		return Boolean.TRUE;
	}

	@Override
	protected Class<UserAccount> __entityClass__() {
		return UserAccount.class;
	}

	@Override
	protected Class<UserAccountSearchFormModel> __queryClass__() {
		return UserAccountSearchFormModel.class;
	}

	@Override
	protected Class<UserAccountSearchResultFormModel> __resultClass__() {
		return UserAccountSearchResultFormModel.class;
	}

	@Override
	protected Collection<UserAccount> __query__() {
		return userAccountBusiness.findByCriteria(new UserAccountSearchCriteria(form.getData().getUsername()));
	}

	@Override
	protected Long __count__() {
		return userAccountBusiness.countByCriteria(new UserAccountSearchCriteria(form.getData().getUsername()));
	}
	
}
