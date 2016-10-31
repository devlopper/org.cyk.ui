package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;

@Named @RequestScoped
public class LogoutPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = -1797753269882644031L;

	public void logout() {
		userSession.logout();
	}

	
}
