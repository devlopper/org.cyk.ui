package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;

import org.cyk.system.root.business.impl.party.person.AbstractActorDetails;
import org.cyk.ui.test.model.Actor;

public class ActorOutputDetails extends AbstractActorDetails.AbstractDefault<Actor> implements Serializable {

	private static final long serialVersionUID = -4675159660834771105L;

	public ActorOutputDetails(Actor actor) {
		super(actor);
	}
	
}
