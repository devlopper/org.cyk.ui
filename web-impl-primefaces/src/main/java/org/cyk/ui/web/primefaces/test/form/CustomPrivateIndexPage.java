package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.web.primefaces.Table;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Named @RequestScoped @Getter @Setter
public class CustomPrivateIndexPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private Table<PersonDetails> tablePersonDetails;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void initialisation() {
		super.initialisation();
		tablePersonDetails = (Table<PersonDetails>) createTable(PersonDetails.class, null, null);
		tablePersonDetails.addColumnFromDataClass();
		
	}
	
	@Override
	protected void afterInitialisation() {
		// TODO Auto-generated method stub
		super.afterInitialisation();
		tablePersonDetails.addRow(new PersonDetails("Ali bongo 2", "12/09/1983"));
	}
	
	/**/
	
	@Getter @Setter @AllArgsConstructor
	private class PersonDetails {
		
		@Input @InputText
		private String names;
		
		@Input @InputText
		private String dateOfBirth;
		
	}
	
}
