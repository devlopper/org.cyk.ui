package org.cyk.ui.api.model.party;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.party.person.Person;

@Getter @Setter
public class DefaultPersonReadFormModel extends AbstractPersonReadFormModel<Person>  implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;

	public DefaultPersonReadFormModel(Person person) {
		super(person);
	}
	
	@Override
	protected Person getPerson(Person person) {
		return person;
	}

	
				
}
