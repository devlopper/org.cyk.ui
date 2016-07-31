package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.api.model.party.AbstractPersonEditFormModel;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class PersonEditPage extends AbstractPersonEditPage<Person> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Override
	protected Person getPerson() {
		return identifiable;
	}
	
	@Getter @Setter
	public static class Form extends AbstractPersonEditFormModel.AbstractDefault.Default<Person> implements Serializable {

		private static final long serialVersionUID = 2646571878912106597L;
		
	}

	
	
}
