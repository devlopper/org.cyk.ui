package org.cyk.system.test.business.impl.actor;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.cyk.system.root.business.impl.party.person.AbstractActorBusinessImpl;
import org.cyk.system.test.business.api.actor.ActorBusiness;
import org.cyk.system.test.model.actor.Actor;
import org.cyk.system.test.persistence.api.actor.ActorDao;

@Stateless
public class ActorBusinessImpl extends AbstractActorBusinessImpl<Actor, ActorDao,Actor.SearchCriteria> implements ActorBusiness,Serializable {

	private static final long serialVersionUID = -7830673760640348717L;

	@Inject
	public ActorBusinessImpl(ActorDao dao) {
		super(dao);
	}
	
}
