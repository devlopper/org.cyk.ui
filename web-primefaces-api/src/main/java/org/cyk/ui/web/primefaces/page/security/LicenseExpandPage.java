package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.cyk.system.root.business.api.Crud;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.web.api.WebNavigationManager;

@Named @RequestScoped
public class LicenseExpandPage extends AbstractLicensePage implements Serializable {

	private static final long serialVersionUID = -3563847253553434464L;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		title=text("page.license.expand.title");
		contentTitle = text("page.license.expand.title");
	}

	@Override
	public void serve(UICommand command, Object parameter) {
		if(form.getSubmitCommandable().getCommand()==command){
			licenseBusiness.expand(identifiable);
			
		}
	}
	
	@Override
	public Object succeed(UICommand command, Object parameter) {
		if(form.getSubmitCommandable().getCommand()==command){
			WebNavigationManager.getInstance().redirectTo(WebNavigationManager.getInstance().getOutcomePrivateIndex());
		}
		return super.succeed(command, parameter);
	}
	
	@Override
	protected Crud crudFromRequestParameter() {
		return Crud.UPDATE;
	}
	
}
