package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.security.UserAccountBusiness;
import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.web.api.WebSession;
import org.omnifaces.util.Faces;

@Named @RequestScoped
public class LoginPage extends AbstractBusinessEntityFormOnePage<Credentials> implements Serializable {

	private static final long serialVersionUID = -1797753269882644031L;

	@Inject private UserAccountBusiness userAccountBusiness;
	
	@Inject private WebSession session;
	@Getter @Setter private Boolean rememberMe = Boolean.TRUE;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		form.setShowCommands(Boolean.TRUE);
	}
	
	@Override
	public void serve(UICommand command, Object parameter) {
		if(form.getSubmitCommandable().getCommand()==command){
			UserAccount userAccount = userAccountBusiness.connect(identifiable);
			SecurityUtils.getSubject().login(new UsernamePasswordToken(identifiable.getUsername(), identifiable.getPassword(),rememberMe));
			session.setUserAccount(userAccount);
		}
	}
	
	@Override
	public Object succeed(UICommand command, Object parameter) {
		if(form.getSubmitCommandable().getCommand()==command){
			SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(Faces.getRequest());
			if(savedRequest==null)
				navigationManager.redirectTo("home");
			else
				navigationManager.redirectToUrl(savedRequest.getRequestUrl());
		}
		return super.succeed(command, parameter);
	}
	
	@Override
	protected BusinessEntityInfos fetchBusinessEntityInfos() {
		BusinessEntityInfos b = new BusinessEntityInfos(Credentials.class, languageBusiness);
		return b;
	}
	
	@Override
	protected Crud crudFromRequestParameter() {
		return Crud.CREATE;
	}
	
	@Override
	public Boolean getShowContentMenu() {
		return Boolean.FALSE;
	}

	
}
