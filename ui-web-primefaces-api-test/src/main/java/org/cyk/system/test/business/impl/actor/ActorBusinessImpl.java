package org.cyk.system.test.business.impl.actor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.cyk.system.root.business.impl.party.person.AbstractActorBusinessImpl;
import org.cyk.system.test.business.api.actor.ActorBusiness;
import org.cyk.system.test.model.actor.Actor;
import org.cyk.system.test.persistence.api.actor.ActorDao;

import lombok.Getter;
import lombok.Setter;

@Stateless
public class ActorBusinessImpl extends AbstractActorBusinessImpl<Actor, ActorDao,Actor.SearchCriteria> implements ActorBusiness,Serializable {

	private static final long serialVersionUID = -7830673760640348717L;

	/*@Inject
	public ActorBusinessImpl(ActorDao dao) {
		super(dao);
	}*/
	
	@Override
	protected Collection<? extends org.cyk.system.root.business.impl.AbstractIdentifiableBusinessServiceImpl.Listener<?>> getListeners() {
		return Listener.COLLECTION;
	}
	
	public static interface Listener extends org.cyk.system.root.business.impl.party.person.AbstractActorBusinessImpl.Listener<Actor>{
		
		Collection<Listener> COLLECTION = new ArrayList<>();
		
		/**/

		public static class Adapter extends org.cyk.system.root.business.impl.party.person.AbstractActorBusinessImpl.Listener.Adapter.Default<Actor> implements Listener, Serializable {
			private static final long serialVersionUID = -1625238619828187690L;
			
			/**/
			@Getter @Setter
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = -1625238619828187690L;
				
				/**/
				
				
			}
			
		}
		
	}
	
}
