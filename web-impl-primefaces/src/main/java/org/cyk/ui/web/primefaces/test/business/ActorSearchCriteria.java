package org.cyk.ui.web.primefaces.test.business;

import org.cyk.system.root.model.party.AbstractActorSearchCriteria;
import org.cyk.ui.test.model.Actor;

public class ActorSearchCriteria extends AbstractActorSearchCriteria<Actor> {

	private static final long serialVersionUID = -7909506438091294611L;

	public ActorSearchCriteria() {
		this(null);
	}

	public ActorSearchCriteria(String name) {
		super(name);
	}
	
	
}
