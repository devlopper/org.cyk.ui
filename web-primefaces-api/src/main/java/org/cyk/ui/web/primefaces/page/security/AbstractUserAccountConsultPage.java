package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.impl.security.UserAccountDetails;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage;

@Getter @Setter
public abstract class AbstractUserAccountConsultPage extends AbstractConsultPage<UserAccount> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private FormOneData<UserAccountDetails> details;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		
		details = createDetailsForm(UserAccountDetails.class, identifiable, new DetailsConfigurationListener.Form.Adapter<UserAccount,UserAccountDetails>(UserAccount.class, UserAccountDetails.class){
			private static final long serialVersionUID = 1L;
			@Override
			public Boolean getEnabledInDefaultTab() {
				return Boolean.TRUE;
			}
		});
		
		//contextualMenu = userSession.getUserAccountMenu();
	}
	
	
	@Override
	protected BusinessEntityInfos fetchBusinessEntityInfos() {
		return uiManager.businessEntityInfos(UserAccount.class);
	}
	
	
}
