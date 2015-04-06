package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;
import java.net.URI;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.BusinessException;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.security.UserAccountBusiness;
import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.ui.api.AbstractUserSession;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.web.api.WebNavigationManager;
import org.cyk.ui.web.primefaces.page.AbstractBusinessEntityFormOnePage;
import org.omnifaces.util.Faces;

@Named @ViewScoped
public class LoginPage extends AbstractBusinessEntityFormOnePage<Credentials> implements Serializable {

	private static final long serialVersionUID = -1797753269882644031L;

	@Inject private UserAccountBusiness userAccountBusiness;
	
	@Getter @Setter private Boolean rememberMe = Boolean.FALSE;
	private Boolean disconnect = Boolean.FALSE;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		contentTitle = uiManager.getApplication().getName();
		form.setShowCommands(Boolean.TRUE);
		form.getSubmitCommandable().setLabel(text("command.login"));	
	}
		
	@Override
	public void serve(UICommand command, Object parameter) {
		if(form.getSubmitCommandable().getCommand()==command){
			if(Boolean.TRUE.equals(disconnect)){
				UserAccount userAccount = userAccountBusiness.findByCredentials(identifiable);
				userAccountBusiness.disconnect(userAccount);
				AbstractUserSession.logout(userAccount);
				disconnect = Boolean.FALSE;
			}
			UserAccount userAccount = userAccountBusiness.connect(identifiable);
			SecurityUtils.getSubject().login(new UsernamePasswordToken(identifiable.getUsername(), identifiable.getPassword(),rememberMe));
			userSession.init(userAccount);
			
		}
	}
	
	@Override
	public Object succeed(UICommand command, Object parameter) {
		if(form.getSubmitCommandable().getCommand()==command){
			SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(Faces.getRequest());
			String home = null;
			if(savedRequest==null)
				home = WebNavigationManager.getInstance().homeUrl(userSession);
			else{
				if(StringUtils.equals(URI.create(savedRequest.getRequestURI()).getPath(), Faces.getRequestContextPath()+"/private/index.jsf"))
					home = navigationManager.homeUrl(userSession);
				else
					home = savedRequest.getRequestUrl();
			}
			
			navigationManager.redirectToUrl(home);
		}
		return super.succeed(command, parameter);
	}
	
	@Override
	public Object fail(UICommand command, Object parameter, Throwable throwable) {
		if(throwable instanceof BusinessException){
			BusinessException businessException = (BusinessException) throwable;
			if(UserAccountBusiness.EXCEPTION_ALREADY_CONNECTED.equals(businessException.getIdentifier()))
				messageDialogOkButtonOnClick = "PF('"+webManager.getConnectionMessageDialogWidgetId()+"').show();" ;
		}
		return super.fail(command, parameter, throwable);
	}
	
	public void disconnectConnectedAndConnect(){
		//userAccountBusiness.disconnect(userAccountBusiness.findByCredentials(identifiable));
		disconnect = Boolean.TRUE;
		form.getSubmitCommandable().getCommand().execute(identifiable);
	}
	
	@Override
	protected BusinessEntityInfos fetchBusinessEntityInfos() {
		return UIManager.getInstance().businessEntityInfos(Credentials.class);
	}
	
	@Override
	protected Crud crudFromRequestParameter() {
		return Crud.CREATE;
	}
	
	@Override
	public Boolean getShowMainMenu() {
		return Boolean.FALSE;
	}
	
	@Override
	public Boolean getShowContentMenu() {
		return Boolean.FALSE;
	}

	
}
