package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.party.person.Person;

import lombok.Getter;
import lombok.Setter;

//@Named @ViewScoped 
@Getter @Setter
public class PersonConsultPage extends AbstractPersonConsultPage<Person> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Override
	protected Person getPerson() {
		return identifiable;
	}
	
}
