package org.cyk.ui.web.api;

import java.io.Serializable;
import java.util.Locale;

import javax.enterprise.context.SessionScoped;

import lombok.Getter;

import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.utility.common.cdi.AbstractBean;

@SessionScoped
public class WebSession extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 7799444210756287076L;

	@Getter private Locale locale = Locale.FRENCH;
	@Getter private UserAccount userAccount;
	
	public Party getUser(){
		return userAccount == null?null:userAccount.getUser();
	}
	
}
