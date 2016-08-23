package org.cyk.system.test.persistence.impl.actor;

import java.io.Serializable;

import org.cyk.system.root.persistence.impl.party.person.AbstractActorDaoImpl;
import org.cyk.system.test.model.actor.Actor;
import org.cyk.system.test.persistence.api.actor.ActorDao;

public class ActorDaoImpl extends AbstractActorDaoImpl<Actor,Actor.SearchCriteria> implements ActorDao,Serializable {

	private static final long serialVersionUID = -1712788156426144935L;

}
