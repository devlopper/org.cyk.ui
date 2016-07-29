package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;
import java.net.URI;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

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
import org.cyk.system.root.business.api.security.OpticalDecoderBusiness;
import org.cyk.system.root.business.api.security.UserAccountBusiness;
import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.ui.api.AbstractUserSession;
import org.cyk.ui.api.Icon;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.model.AbstractOpticalBarCodeReader;
import org.cyk.ui.api.model.OpticalBarCodeReaderAdapter;
import org.cyk.ui.web.api.WebManager;
import org.cyk.ui.web.api.WebNavigationManager;
import org.cyk.ui.web.primefaces.Timer;
import org.cyk.ui.web.primefaces.page.AbstractBusinessEntityFormOnePage;
import org.omnifaces.util.Faces;

@Named @ViewScoped
public class LoginPage extends AbstractBusinessEntityFormOnePage<Credentials> implements Serializable {

	private static final long serialVersionUID = -1797753269882644031L;

	@Inject private UserAccountBusiness userAccountBusiness;
	@Inject private OpticalDecoderBusiness opticalDecoderBusiness;
	
	@Getter private OpticalBarCodeReader opticalBarCodeReader;
	@Getter @Setter private Boolean rememberMe = Boolean.FALSE;
	private Boolean disconnect = Boolean.FALSE;
	//@Getter private InputText inputText;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		contentTitle = uiManager.getApplication().getName();
		form.setShowCommands(Boolean.TRUE);
		form.getSubmitCommandable().setLabel(text("command.login"));	
		form.getSubmitCommandable().setIcon(Icon.ACTION_LOGIN);
		form.setFieldsRequiredMessage(null);
		
		opticalBarCodeReader = new OpticalBarCodeReader("tabview:obcr",opticalDecoderBusiness);
		opticalBarCodeReader.getOpticalBarCodeReaderListeners().add(new OpticalBarCodeReaderAdapter<Timer>(){
			private static final long serialVersionUID = 1519936596082983556L;
			@Override
			public void notNullString(AbstractOpticalBarCodeReader<Timer> reader, byte[] bytes,String value) {
				String[] credentials = StringUtils.split(value, "\r\n");
				//connect(new Credentials(credentials[0], credentials[1]));
				identifiable.setUsername(StringUtils.trim(credentials[0]));
				identifiable.setPassword(StringUtils.trim(credentials[1]));
				form.getSubmitCommandable().getCommand().execute(new Credentials(credentials[0], credentials[1]));
			}
		});
	}
	
	@Override
	protected String buildContentTitle() {
		return text("view.login.content.title");
	}
	
	/*@Override
	public String getContentTitle() {
		return text("view.login.content.title");
	}*/
		
	@Override
	public void serve(UICommand command, Object parameter) {
		if(form.getSubmitCommandable().getCommand()==command){
			//connect(identifiable);
			connect(/*(Credentials) parameter*/ (Credentials)parameter );
		}
	}
	
	public void connect(Credentials credentials){
		if(Boolean.TRUE.equals(disconnect)){
			UserAccount userAccount = userAccountBusiness.findByCredentials(credentials);
			userAccountBusiness.disconnect(userAccount);
			AbstractUserSession.logout(userAccount);
			disconnect = Boolean.FALSE;
		}
		UserAccount userAccount = userAccountBusiness.connect(credentials);
		SecurityUtils.getSubject().login(new UsernamePasswordToken(credentials.getUsername(), credentials.getPassword(),rememberMe));
		userSession.init(userAccount);
		((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(Boolean.FALSE)).setAttribute(WebManager.getInstance().getSessionAttributeUserSession(), userSession);
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
	
	/**/

}
