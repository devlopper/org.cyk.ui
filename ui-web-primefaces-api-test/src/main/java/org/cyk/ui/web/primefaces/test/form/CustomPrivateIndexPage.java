package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.ui.web.primefaces.Table;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class CustomPrivateIndexPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private Table<PersonDetails> tablePersonDetails;
	
	//private ItemCollection<PersonDetails> personCollection = new ItemCollection<>("qwerty",PersonDetails.class);
	 
	@SuppressWarnings("unchecked")
	@Override
	protected void initialisation() {
		super.initialisation();
		tablePersonDetails = (Table<PersonDetails>) createTable(PersonDetails.class, null, null);
	}
	
	@Override
	protected void afterInitialisation() {
		// TODO Auto-generated method stub
		super.afterInitialisation();
		tablePersonDetails.addRow(new PersonDetails("Ali bongo 2", "12/09/1983"));
		//personCollection.setLabel("Details de personnes");
		//System.out.println("PrivateIndexPage.initialisation()");
		//throw new RuntimeException();
	}
	
	/**/
	
	@Getter @Setter @AllArgsConstructor @NoArgsConstructor
	public static class PersonDetails {
		
		@Input @InputText
		private String names;
		
		@Input @InputText
		private String dateOfBirth;
		
	}
	
}
