package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.cyk.system.root.business.api.Crud;
import org.cyk.ui.api.command.UICommand;

@Named @RequestScoped
public class LicenseExpiredPage extends AbstractLicensePage implements Serializable {

	private static final long serialVersionUID = -3563847253553434464L;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		title=text("page.license.expired.title");
		contentTitle = text("page.license.expired.title");
	}
	
	@Override
	public Boolean getShowMainMenu() {
		return Boolean.FALSE;
	}
	
	@Override
	protected Crud crudFromRequestParameter() {
		return Crud.READ;
	}

	@Override
	public void serve(UICommand command, Object parameter) {
		
	}

}
