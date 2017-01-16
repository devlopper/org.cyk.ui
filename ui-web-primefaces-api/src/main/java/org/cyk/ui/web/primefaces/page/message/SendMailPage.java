package org.cyk.ui.web.primefaces.page.message;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.api.command.CommandAdapter;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.web.primefaces.page.AbstractFormPage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputChoiceAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputManyAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputManyChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.annotation.user.interfaces.InputTextarea;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Named @ViewScoped
public class SendMailPage extends AbstractFormPage<SendMailPage.Form> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	protected void initialisation() {
		super.initialisation();
		form.getSubmitCommandable().getCommand().getCommandListeners().add(new CommandAdapter(){
			private static final long serialVersionUID = -4119943624542439662L;
			@Override
			public void serve(UICommand command, Object parameter) {
				super.serve(command, parameter);
				
			}
		});
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		form.findInputByClassByFieldName(org.cyk.ui.api.data.collector.control.InputManyAutoComplete.class, "persons").getCommon().setQueryResultsCacheEnabled(Boolean.FALSE);
	}
	
	/**/
	
	@Getter @Setter
	public static class Form implements Serializable {

		private static final long serialVersionUID = 1L;
		
		@Input @InputChoice @InputChoiceAutoComplete @InputManyChoice @InputManyAutoComplete private List<Person> persons;
		//@Input @InputChoice @InputChoiceAutoComplete @InputManyChoice @InputManyAutoComplete private List<ElectronicMail> electronicMails;
		//@Input @InputText private String otherElectronicMails;
		@Input @InputText private String title;
		@Input @InputTextarea private String message;
		
	}
	
}
