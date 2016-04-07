package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;

import org.cyk.ui.api.model.party.AbstractActorOutputDetails;
import org.cyk.ui.test.model.Actor;

public class ActorOutputDetails extends AbstractActorOutputDetails<Actor> implements Serializable {

	private static final long serialVersionUID = -4675159660834771105L;

	public ActorOutputDetails(Actor actor) {
		super(actor);
	}
	
}
