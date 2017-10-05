package org.cyk.ui.web.primefaces.page.geography;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.model.geography.PhoneNumberType;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class PhoneNumberEditPage extends AbstractContactEditPage<PhoneNumber> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
		
	public static class Form extends AbstractForm<PhoneNumber> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputChoice @InputOneChoice @InputOneCombo @NotNull private Country country;
		@Input @InputText @NotNull private String number;
		@Input @InputChoice @InputOneChoice @InputOneCombo @NotNull private PhoneNumberType type;
		
		public static final String FIELD_COUNTRY = "country";
		public static final String FIELD_NUMBER = "number";
		public static final String FIELD_TYPE = "type";
	}
	
}
