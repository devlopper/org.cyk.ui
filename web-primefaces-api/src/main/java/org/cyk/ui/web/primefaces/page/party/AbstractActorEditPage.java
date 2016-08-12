package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Person;

@Getter @Setter
public abstract class AbstractActorEditPage<ACTOR extends AbstractIdentifiable> extends AbstractPersonEditPage<ACTOR> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	protected abstract AbstractActor getActor();
	
	@Override
	protected Person getPerson() {
		return getActor().getPerson();
	}
	

	/**/
	
	/*public static abstract class AbstractPageAdapter<ACTOR extends AbstractIdentifiable> extends AbstractPersonEditPage.AbstractPageAdapter<ACTOR> implements Serializable {

		private static final long serialVersionUID = 4370361826462886031L;

		public AbstractPageAdapter(Class<ACTOR> entityTypeClass) {
			super(entityTypeClass);	
		}
	
		
		
		public static class Default<ACTOR extends AbstractActor> extends AbstractPageAdapter<ACTOR> implements Serializable {

			private static final long serialVersionUID = 4370361826462886031L;

			public Default(Class<ACTOR> entityTypeClass) {
				super(entityTypeClass);	
			}
			
		}
	}*/
}
