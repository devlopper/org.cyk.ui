package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.person.AbstractActor;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractActorListPage<ACTOR extends AbstractIdentifiable> extends AbstractPersonListPage<ACTOR> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	/**/
	
	public static abstract class AbstractActorListPageAdapter<ACTOR extends AbstractIdentifiable> extends AbstractPersonListPageAdapter<ACTOR> implements Serializable {

		private static final long serialVersionUID = 4370361826462886031L;

		public AbstractActorListPageAdapter(Class<ACTOR> entityTypeClass) {
			super(entityTypeClass);
		}
		
		/**/
		
		public static class Default extends AbstractActorListPageAdapter<AbstractActor> implements Serializable{

			private static final long serialVersionUID = -2256223077759190879L;

			public Default() {
				super(AbstractActor.class);
			}
			
		}
		
	}
}
