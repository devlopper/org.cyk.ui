package org.cyk.ui.api;

import java.io.Serializable;
import java.util.Locale;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.security.UserAccountBusiness;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.ui.api.command.menu.ApplicationMenuManager;
import org.cyk.ui.api.command.menu.UIMenu;
import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractUserSession extends AbstractBean implements UserSession,Serializable {

	private static final long serialVersionUID = 958643519183802472L;

	@Inject protected UserAccountBusiness userAccountBusiness;
	
	@Getter @Setter protected Locale locale = Locale.FRENCH;
	@Getter @Setter protected UserAccount userAccount;
	@Getter @Setter protected UIMenu applicationMenu,referenceEntityMenu;
		
	public Party getUser(){
		return userAccount == null?null:userAccount.getUser();
	}
	
	@Override
	public Boolean getLoggedIn() {
		return userAccount!=null;
	}
	
	public void init(UserAccount userAccount){
		setUserAccount(userAccount);
		setApplicationMenu(ApplicationMenuManager.getInstance().build(this));
		setReferenceEntityMenu(ApplicationMenuManager.getInstance().referenceEntity(this));
	}
	
	protected abstract void __logout__();
	
	public void logout(){
		userAccountBusiness.disconnect(getUserAccount());
		__logout__();
		__invalidateSession__();
		__navigateToPublicIndex__();
	}
	
	
	protected abstract void __navigateToPublicIndex__();
	
	protected abstract void __invalidateSession__();
}
