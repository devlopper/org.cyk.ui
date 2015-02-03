package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonSearchCriteria;
import org.cyk.ui.api.model.PersonFormModel;
import org.cyk.ui.web.primefaces.page.AbstractBusinessQueryPage;

@Named
@ViewScoped
@Getter
@Setter
public class PersonSearchPage extends AbstractBusinessQueryPage<Person,PersonFormModel, PersonFormModel> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Inject private PersonBusiness personBusiness;
	
	@Override
	protected Class<Person> __entityClass__() {
		return Person.class;
	}
	
	@Override
	protected Class<PersonFormModel> __queryClass__() {
		return PersonFormModel.class;
	}

	@Override
	protected Class<PersonFormModel> __resultClass__() {
		return PersonFormModel.class;
	}

	@Override
	protected Collection<Person> __query__() {
		return personBusiness.findByCriteria(new PersonSearchCriteria(query.getFirstName()));
	}

	@Override
	protected Long __count__() {
		return personBusiness.countByCriteria(new PersonSearchCriteria(query.getFirstName()));
	}	
	
	
	
}
