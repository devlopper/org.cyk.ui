package org.cyk.ui.web.primefaces.page.application;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.model.party.Application;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.web.api.WebNavigationManager;
import org.cyk.ui.web.primefaces.page.AbstractBusinessEntityFormOnePage;

@Named @RequestScoped
public class ApplicationInstallationPage extends AbstractBusinessEntityFormOnePage<Application> implements Serializable {

	private static final long serialVersionUID = -3563847253553434464L;
	
	@Inject private ApplicationBusiness applicationBusiness;
	
	private ApplicationInstallationFormModel formModel = new ApplicationInstallationFormModel(new Application());
	
	@Override
	protected void initialisation() {
		super.initialisation();
		title=text("page.application.installation.title");
		contentTitle = text("page.application.installation.title");
	}

	@Override
	public void serve(UICommand command, Object parameter) {
		if(form.getSubmitCommandable().getCommand()==command){
			applicationBusiness.install(formModel.getInstallation());
			
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
	protected Object data(Class<?> aClass) {
		return formModel;
	}
	
	@Override
	protected BusinessEntityInfos fetchBusinessEntityInfos() {
		return uiManager.businessEntityInfos(Application.class);
	}
	
	@Override
	protected Crud crudFromRequestParameter() {
		return Crud.CREATE;
	}
	
	@Override
	public Boolean getShowMainMenu() {
		return Boolean.FALSE;
	}
	
}
