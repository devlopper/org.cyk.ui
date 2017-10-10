package org.cyk.ui.web.primefaces.page.party.person;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.impl.party.person.PersonBusinessImpl;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.web.primefaces.data.collector.control.OutputCollection;
import org.cyk.ui.web.primefaces.page.party.AbstractPersonListPage;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class PersonListPage extends AbstractPersonListPage<Person> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	private OutputCollection<Person> collection;
	
	@Override
	protected void initialisation() {
		// TODO Auto-generated method stub
		super.initialisation();
		/*collection = new OutputCollection<Person>(Person.class,inject(PersonBusiness.class).findAll());
		collection.addElementObjectFieldsAsColumns(PersonBusinessImpl.Details.FIELD_CODE,PersonBusinessImpl.Details.FIELD_NAME,PersonBusinessImpl.Details.FIELD_LASTNAMES
				,PersonBusinessImpl.Details.FIELD_BIRTH_DATE);
		collection.get__nameColumn__().setIsShowable(Boolean.FALSE);
		*/
		//collection.getCollection().setMany(inject(PersonBusiness.class).findAll());
	}
	
	/**/
	
	
}
