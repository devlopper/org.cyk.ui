package org.cyk.ui.web.primefaces.page.geography;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.geography.PostalBox;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.hibernate.validator.constraints.Email;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class PostalBoxEditPage extends AbstractContactEditPage<PostalBox> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
		
	public static class Form extends AbstractForm<PostalBox> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputText @NotNull @Email private String address;
		
		public static final String FIELD_ADDRESS = "address";
	}
	
}
