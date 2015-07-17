package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.security.UserAccountBusiness;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.model.security.UserAccountSearchCriteria;
import org.cyk.ui.api.data.collector.control.Input;
import org.cyk.ui.api.data.collector.form.ControlSet;
import org.cyk.ui.web.primefaces.page.AbstractBusinessQueryPage;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

@Named @ViewScoped @Getter @Setter
public class UserAccountsPage extends AbstractBusinessQueryPage<UserAccount, UserAccountSearchFormModel, UserAccountSearchResultFormModel> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Inject private UserAccountBusiness userAccountBusiness;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		contextualMenu = userSession.getSecurityMenu();
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
	
	private UserAccountSearchCriteria searchCriteria(){
		UserAccountSearchCriteria searchCriteria = new UserAccountSearchCriteria(form.getData().getUsername());
		searchCriteria.getRoleExcluded().add(RootBusinessLayer.getInstance().getAdministratorRole());
		return searchCriteria;
	}

	@Override
	protected Collection<UserAccount> __query__() {
		return userAccountBusiness.findByCriteria(searchCriteria());
	}

	@Override
	protected Long __count__() {
		return userAccountBusiness.countByCriteria(searchCriteria());
	}

	@Override
	public void input(
			ControlSet<UserAccount, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> controlSet,
			Input<?, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> input) {
		
	}
	
}
