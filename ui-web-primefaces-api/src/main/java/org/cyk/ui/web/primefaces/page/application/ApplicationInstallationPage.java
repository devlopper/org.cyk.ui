package org.cyk.ui.web.primefaces.page.application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.shiro.SecurityUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.model.party.Application;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.web.api.WebNavigationManager;
import org.cyk.ui.web.primefaces.page.AbstractBusinessEntityFormOnePage;
import org.omnifaces.util.Faces;

@Named @RequestScoped
public class ApplicationInstallationPage extends AbstractBusinessEntityFormOnePage<Application> implements Serializable {

	private static final long serialVersionUID = -3563847253553434464L;
	
	public static final Collection<ApplicationInstallListener> LISTENERS = new ArrayList<>();
	@Inject private ApplicationBusiness applicationBusiness;
	
	private ApplicationInstallationFormModel formModel = new ApplicationInstallationFormModel(new Application());
	
	@Override
	protected void initialisation() {
		super.initialisation();
		title=text("page.application.installation.title");
		contentTitle = text("page.application.installation.title");
		
		//TODO to be removed 
		/*formModel.getAdministratorCredentials().getCredentialsInputs().getCredentials().setUsername("admin");
		formModel.getAdministratorCredentials().getCredentialsInputs().getCredentials().setPassword("123");
		formModel.getAdministratorCredentials().getCredentialsInputs().setPasswordConfirmation("123");
		
		formModel.getManagerCredentials().setName("TheManager");
		formModel.getManagerCredentials().getCredentialsInputs().getCredentials().setUsername("manager");
		formModel.getManagerCredentials().getCredentialsInputs().getCredentials().setPassword("123");
		formModel.getManagerCredentials().getCredentialsInputs().setPasswordConfirmation("123");
		
		formModel.getLicense().getPeriod().setFromDate(DateUtils.addDays(new Date(), -1));
		formModel.getLicense().getPeriod().setToDate(DateUtils.addDays(new Date(), 30));*/
	}

	@Override
	public void serve(UICommand command, Object parameter) {
		if(form.getSubmitCommandable().getCommand()==command){
			applicationBusiness.install(formModel.getInstallation());
			for(ApplicationInstallListener listener : LISTENERS)
				listener.install(formModel);
		}
	}
	
	@Override
	public Object succeed(UICommand command, Object parameter) {
		if(form.getSubmitCommandable().getCommand()==command){
			SecurityUtils.getSubject().logout();
			Faces.invalidateSession();
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
	
	/**/
	
	public static interface ApplicationInstallListener{
		void install(ApplicationInstallationFormModel formModel);
	}
	
}
