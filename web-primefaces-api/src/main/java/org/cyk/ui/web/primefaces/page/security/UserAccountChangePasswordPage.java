package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.web.api.WebInputListener;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputPassword;
import org.cyk.utility.common.validation.Client;

@Named @ViewScoped @Getter @Setter
public class UserAccountChangePasswordPage extends AbstractCrudOnePage<UserAccount> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		addInputListener(Form.FIELD_CURRENT_PASSWORD, new WebInputListener.Adapter(){
			private static final long serialVersionUID = 7526066306750441853L;
			@Override
			public void validate(FacesContext facesContext,UIComponent uiComponent, Object value) throws ValidatorException {
				if(!identifiable.getCredentials().getPassword().equals(value))
					webManager.throwValidationException("invalidcurrentpassword", new Object[]{});
			}
		});
		addInputListener(Form.FIELD_NEW_PASSWORD_CONFIRMATION, new WebInputListener.Adapter(){
			private static final long serialVersionUID = 7526066306750441853L;
			@Override
			public void validate(FacesContext facesContext,UIComponent uiComponent, Object value) throws ValidatorException {
				debug(((Form)form.getData()));
				//if(!identifiable.getCredentials().getPassword().equals(value))
				//	webManager.throwValidationExceptionUnknownValue(value);
			}
		});
	}
	
	@Override
	protected void update() {
		// TODO Auto-generated method stub
		//super.update();
	}
	
	@Override
	protected BusinessEntityInfos fetchBusinessEntityInfos() {
		return uiManager.businessEntityInfos(UserAccount.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected <T extends AbstractIdentifiable> T identifiableFromRequestParameter(Class<T> aClass) {
		return (T) getUserSession().getUserAccount();
	}
	
	@Override
	protected Crud crudFromRequestParameter() {
		return Crud.UPDATE;
	}

	@Override
	protected Class<? extends AbstractFormModel<?>> __formModelClass__() {
		return Form.class;
	}
	
	/**/
	
	@Getter @Setter
	public static class Form extends AbstractFormModel<UserAccount> implements Serializable {

		private static final long serialVersionUID = -6718318186450819203L;

		@Input @InputPassword @NotNull(groups={Client.class})
		private String currentPassword;
		
		@Input @InputPassword @NotNull(groups={Client.class})
		private String newPassword;
		
		@Input @InputPassword @NotNull(groups={Client.class})
		private String newPasswordConfirmation;
		
		@Override
		public void write() {
			super.write();
			identifiable.getCredentials().setPassword(newPassword);
		}
		
		/**/
		
		public static final String FIELD_CURRENT_PASSWORD = "currentPassword";
		public static final String FIELD_NEW_PASSWORD = "newPassword";
		public static final String FIELD_NEW_PASSWORD_CONFIRMATION = "newPasswordConfirmation";
	}

	
}
