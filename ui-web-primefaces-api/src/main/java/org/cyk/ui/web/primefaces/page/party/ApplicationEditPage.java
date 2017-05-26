package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.message.SmtpProperties;
import org.cyk.system.root.model.party.Application;
import org.cyk.ui.api.model.AbstractBusinessIdentifiedEditFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputBooleanButton;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Named @ViewScoped
public class ApplicationEditPage extends AbstractCrudOnePage<Application> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Getter @Setter
	public static class Form extends AbstractBusinessIdentifiedEditFormModel<Application> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputChoice @InputOneChoice @InputOneCombo private SmtpProperties smtpProperties;
		
		@Input @InputBooleanButton private Boolean uniformResourceLocatorFiltered;
		
		@Input @InputText @NotNull private String webContext;
		
		/**/
		
		public static final String FIELD_SMTP_PROPERTIES = "smtpProperties";
		public static final String FIELD_UNIFORM_RESOURCE_LOCATOR_FILTERED = "uniformResourceLocatorFiltered";
		public static final String FIELD_WEB_CONTEXT = "webContext";
	}

}
