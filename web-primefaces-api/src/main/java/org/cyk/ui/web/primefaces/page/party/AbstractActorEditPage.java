package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Person;

@Getter @Setter
public abstract class AbstractActorEditPage<ACTOR extends AbstractIdentifiable> extends AbstractPersonEditPage<ACTOR> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	protected abstract AbstractActor getActor();
	
	@Override
	protected Person getPerson() {
		return getActor().getPerson();
	}
	

	
}
