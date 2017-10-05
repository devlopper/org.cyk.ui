package org.cyk.ui.web.primefaces.page.geography;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.geography.ElectronicMailAddress;
import org.cyk.ui.web.api.data.collector.control.WebInput;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.internal.constraintvalidators.EmailValidator;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class ElectronicMailAddressEditPage extends AbstractContactEditPage<ElectronicMailAddress> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		addInputListener(Form.FIELD_ADDRESS, new WebInput.Listener.Adapter.Default(){
			private static final long serialVersionUID = -6937448701586032931L;
			@Override
			public void validate(FacesContext facesContext,UIComponent uiComponent, Object value)throws ValidatorException {
				if(Boolean.FALSE.equals(new EmailValidator().isValid((CharSequence) value, null)))
					webManager.throwValidationException();
			}
		});
	}
	
	public static class Form extends AbstractForm<ElectronicMailAddress> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputText @NotNull @Email private String address;
		
		public static final String FIELD_ADDRESS = "address";
	}
	
	
}
