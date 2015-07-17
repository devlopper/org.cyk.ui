package org.cyk.ui.api.model.party;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.party.person.Person;

@Getter @Setter @NoArgsConstructor
public class PersonConsultFormModel extends AbstractPersonConsultFormModel<Person>  implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;

	@Override
	protected Person getPerson() {
		return identifiable;
	}
				
}
