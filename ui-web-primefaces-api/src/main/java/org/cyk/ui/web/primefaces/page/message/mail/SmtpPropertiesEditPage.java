package org.cyk.ui.web.primefaces.page.message.mail;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.geography.ElectronicMail;
import org.cyk.system.root.model.message.SmtpProperties;
import org.cyk.system.root.model.network.Service;
import org.cyk.system.root.model.security.Credentials;
import org.cyk.ui.api.model.AbstractBusinessIdentifiedEditFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputChoiceAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputOneAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class SmtpPropertiesEditPage extends AbstractCrudOnePage<SmtpProperties> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	/**/
	
	@Getter @Setter
	public static class Form extends AbstractBusinessIdentifiedEditFormModel<SmtpProperties> implements Serializable {

		private static final long serialVersionUID = 2646571878912106597L;
		
		@Input @InputChoice @InputChoiceAutoComplete @InputOneChoice @InputOneAutoComplete private Service service;
		@Input @InputChoice @InputChoiceAutoComplete @InputOneChoice @InputOneAutoComplete private Credentials credentials;
		@Input @InputChoice @InputChoiceAutoComplete @InputOneChoice @InputOneAutoComplete private ElectronicMail from;
		
		public static final String FIELD_SERVICE = "service";
		public static final String FIELD_FROM = "from";
		public static final String FIELD_CREDENTIALS = "credentials";
	}

}
