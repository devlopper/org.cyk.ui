package org.cyk.ui.web.primefaces.page.geography;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.geography.ElectronicMail;
import org.cyk.ui.web.api.data.collector.control.WebInput;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.internal.constraintvalidators.EmailValidator;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class ElectronicMailEditPage extends AbstractContactEditPage<ElectronicMail> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		addInputListener(Form.FIELD_VALUE, new WebInput.Listener.Adapter.Default(){
			private static final long serialVersionUID = -6937448701586032931L;
			@Override
			public void validate(FacesContext facesContext,UIComponent uiComponent, Object value)throws ValidatorException {
				if(Boolean.FALSE.equals(new EmailValidator().isValid((CharSequence) value, null)))
					webManager.throwValidationException();
			}
		});
	}
	
	public static class Form extends AbstractForm<ElectronicMail> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Override
		public void write() {
			super.write();
			identifiable.setAddress(value);
		}
		
		@Override @Email
		public String getValue() {
			return super.getValue();
		}
	}
	
	
}
