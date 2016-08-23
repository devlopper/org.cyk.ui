package org.cyk.system.test.model.actor;

import java.io.Serializable;

import org.cyk.system.root.business.impl.party.person.AbstractActorDetails;
import org.cyk.system.test.model.actor.Actor;

public class ActorDetails extends AbstractActorDetails.AbstractDefault<Actor> implements Serializable {

	private static final long serialVersionUID = -4675159660834771105L;

	public ActorDetails(Actor actor) {
		super(actor);
	}
	
}
