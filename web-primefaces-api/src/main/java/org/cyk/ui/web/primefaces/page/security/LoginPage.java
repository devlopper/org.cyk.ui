package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;
import java.net.URI;

import javax.enterprise.context.RequestScoped;
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
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.security.UserAccountBusiness;
import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.web.api.WebNavigationManager;
import org.cyk.ui.web.api.WebSession;
import org.cyk.ui.web.primefaces.page.AbstractBusinessEntityFormOnePage;
import org.omnifaces.util.Faces;

@Named @RequestScoped
public class LoginPage extends AbstractBusinessEntityFormOnePage<Credentials> implements Serializable {

	private static final long serialVersionUID = -1797753269882644031L;

	@Inject private UserAccountBusiness userAccountBusiness;
	
	@Inject private WebSession session;
	@Getter @Setter private Boolean rememberMe = Boolean.FALSE;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		form.setShowCommands(Boolean.TRUE);
		form.getSubmitCommandable().setLabel(text("command.login"));		
	}
		
	@Override
	public void serve(UICommand command, Object parameter) {
		if(form.getSubmitCommandable().getCommand()==command){
			UserAccount userAccount = userAccountBusiness.connect(identifiable);
			SecurityUtils.getSubject().login(new UsernamePasswordToken(identifiable.getUsername(), identifiable.getPassword(),rememberMe));
			session.init(userAccount);
		}
	}
	
	@Override
	public Object succeed(UICommand command, Object parameter) {
		if(form.getSubmitCommandable().getCommand()==command){
			SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(Faces.getRequest());
			String home = null;
			if(savedRequest==null)
				home = WebNavigationManager.getInstance().homeUrl(session);
			else{
				if(StringUtils.equals(URI.create(savedRequest.getRequestURI()).getPath(), Faces.getRequestContextPath()+"/private/index.jsf"))
					home = navigationManager.homeUrl(session);
				else
					home = savedRequest.getRequestUrl();
			}
			
			navigationManager.redirectToUrl(home);
		}
		return super.succeed(command, parameter);
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
