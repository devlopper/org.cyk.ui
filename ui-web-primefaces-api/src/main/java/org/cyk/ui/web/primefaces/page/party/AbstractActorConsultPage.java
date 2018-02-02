package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Person;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractActorConsultPage<ACTOR extends AbstractActor> extends AbstractPersonConsultPage<ACTOR> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Override
	protected Person getPerson() {
		return (Person) identifiable.getPerson();
	}
	
	public static class ActorDetailsFormOneDataConfigurationAdapter<DETAILS extends AbstractOutputDetails<AbstractActor>> extends DetailsConfigurationListener.Form.Adapter<AbstractActor,DETAILS>{

		private static final long serialVersionUID = -9101575271431241099L;

		public ActorDetailsFormOneDataConfigurationAdapter(Class<AbstractActor> actorClass,Class<DETAILS> detailsClass) {
			super(actorClass, detailsClass);
		}
		
	}
	
	public static class Adapter<ACTOR extends AbstractActor> extends AbstractActorConsultPageAdapter.Default<ACTOR>{
		private static final long serialVersionUID = -5657492205127185872L;
		
		public Adapter(Class<ACTOR> entityTypeClass) {
			super(entityTypeClass);
		}
		
	}
}
