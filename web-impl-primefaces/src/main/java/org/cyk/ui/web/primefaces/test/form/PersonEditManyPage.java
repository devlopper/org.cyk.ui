package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.api.model.AbstractItemCollectionItem;
import org.cyk.ui.web.primefaces.page.crud.AbstractEditManyPage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class PersonEditManyPage extends AbstractEditManyPage<Person,PersonEditManyPage.PersonEditFormModel> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Inject private PersonBusiness personBusiness;
	
	
	
	@Override
	protected void initialisation() {
		super.initialisation();
		
		
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		
	}
	
	@Override
	protected Class<PersonEditFormModel> getItemCollectionItemClass() {
		// TODO Auto-generated method stub
		return PersonEditFormModel.class;
	}
		
	@Getter @Setter
	public static class PersonEditFormModel extends AbstractItemCollectionItem<Person> implements Serializable{
		
		private static final long serialVersionUID = -829786138986362643L;

		@Input @InputText
		private String name;
		
		@Input @InputText
		private String lastName;
		
	}
	
}
