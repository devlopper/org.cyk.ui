package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;
import java.util.Date;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.security.License;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputCalendar;
import org.cyk.utility.common.annotation.user.interfaces.InputCalendar.Format;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice.ChoiceSet;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneRadio;

@Named @ViewScoped @Getter @Setter
public class LicenseEditPage extends AbstractCrudOnePage<License> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Override
	protected Crud crudFromRequestParameter() {
		return Crud.UPDATE;
	}
	
	/**/
	
	@Getter @Setter
	public static class Form extends AbstractFormModel<License> implements Serializable {

		private static final long serialVersionUID = 2646571878912106597L;
		
		//@Input @InputFile(extensions=@FileExtensions(groups=FileExtensionGroup.RICH_TEXT)) private File file;
		@Input @InputCalendar(format=Format.DATETIME_SHORT) @NotNull private Date expirationDate;
		@Input @InputChoice(set=ChoiceSet.YES_NO) @InputOneChoice @InputOneRadio @NotNull private Boolean expirable;
		@Input @InputChoice(set=ChoiceSet.YES_NO) @InputOneChoice @InputOneRadio @NotNull private Boolean expired;
		
		@Override
		public void read() {
			super.read();
			expirationDate = identifiable.getPeriod().getToDate();
		}
		
		@Override
		public void write() {
			super.write();
			identifiable.getPeriod().setToDate(expirationDate);
		}
		
	}

}
