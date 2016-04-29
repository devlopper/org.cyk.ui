package org.cyk.ui.web.primefaces.test.business;

import java.io.Serializable;

import org.cyk.system.root.persistence.impl.party.person.AbstractActorDaoImpl;
import org.cyk.ui.test.model.Actor;

public class ActorDaoImpl extends AbstractActorDaoImpl<Actor,Actor.SearchCriteria> implements ActorDao,Serializable {

	private static final long serialVersionUID = -1712788156426144935L;

}
