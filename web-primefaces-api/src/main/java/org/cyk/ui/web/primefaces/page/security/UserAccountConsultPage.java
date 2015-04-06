package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Named @ViewScoped @Getter @Setter
public class UserAccountConsultPage extends AbstractConsultPage<UserAccount> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private FormOneData<UserAccountDetails> userAccountDetails;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		
		userAccountDetails = (FormOneData<UserAccountDetails>) createFormOneData(new UserAccountDetails(identifiable), Crud.READ);
		configureDetailsForm(userAccountDetails);
		
		contextualMenu = userSession.getUserAccountMenu();
	}
	
	@Override
	protected BusinessEntityInfos fetchBusinessEntityInfos() {
		return uiManager.businessEntityInfos(UserAccount.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected <T extends AbstractIdentifiable> T identifiableFromRequestParameter(Class<T> aClass) {
		return (T) getUserSession().getUserAccount();
		//return super.identifiableFromRequestParameter(aClass);
	}
	
	/**/
	
	@Getter @Setter
	private class UserAccountDetails implements Serializable{
		
		private static final long serialVersionUID = 5927796963707734255L;
		@Input @InputText
		private String names,username,password="**********";
		
		public UserAccountDetails(UserAccount userAccount) {
			names = ((Person)userAccount.getUser()).getName();
			username = userAccount.getCredentials().getUsername();
		}
	}
	
}
