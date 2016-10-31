package org.cyk.system.test.model.actor;

import java.io.Serializable;

import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.test.model.actor.Actor;
import org.cyk.ui.api.model.party.AbstractActorEditFormModel;

public class ActorFormModel extends AbstractActorEditFormModel<Actor> implements Serializable {

	private static final long serialVersionUID = -4675159660834771105L;

	@Override
	protected AbstractActor getActor() {
		return identifiable;
	}

}
