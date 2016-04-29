package org.cyk.ui.web.primefaces.test.business;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.cyk.system.root.business.impl.party.person.AbstractActorBusinessImpl;
import org.cyk.ui.test.model.Actor;

@Stateless
public class ActorBusinessImpl extends AbstractActorBusinessImpl<Actor, ActorDao,Actor.SearchCriteria> implements ActorBusiness,Serializable {

	private static final long serialVersionUID = -7830673760640348717L;

	@Inject
	public ActorBusinessImpl(ActorDao dao) {
		super(dao);
	}
	
}
