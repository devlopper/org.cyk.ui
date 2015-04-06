package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.security.UserAccountBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.web.primefaces.page.AbstractBusinessEntityFormOnePage;

@Named @ViewScoped @Getter @Setter
public class UserAccountChangePasswordPage extends AbstractBusinessEntityFormOnePage<UserAccount> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Inject private UserAccountBusiness userAccountBusiness;
	
	@Override
	public void serve(UICommand command, Object parameter) {
		userAccountBusiness.update(identifiable);
	}
	
	@Override
	protected BusinessEntityInfos fetchBusinessEntityInfos() {
		return uiManager.businessEntityInfos(UserAccount.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected <T extends AbstractIdentifiable> T identifiableFromRequestParameter(Class<T> aClass) {
		return (T) getUserSession().getUserAccount();
	}
	
	@Override
	protected Crud crudFromRequestParameter() {
		return Crud.UPDATE;
	}

	@Override
	protected Class<? extends AbstractFormModel<?>> __formModelClass__() {
		return UserAccountChangePasswordFormModel.class;
	}
	
}
