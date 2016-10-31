package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.api.model.party.AbstractActorEditFormModel;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractActorEditPage<ACTOR extends AbstractIdentifiable> extends AbstractPersonEditPage<ACTOR> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	protected abstract AbstractActor getActor();
	
	@Override
	protected Person getPerson() {
		return getActor().getPerson();
	}
	
	@Getter @Setter
	public static abstract class AbstractDefault<ACTOR extends AbstractIdentifiable> extends AbstractActorEditPage<ACTOR> implements Serializable {
		private static final long serialVersionUID = 1L;

		/**/
		
		@Getter @Setter
		public static class Default<ACTOR extends AbstractActor> extends AbstractDefault<ACTOR> implements Serializable {
			private static final long serialVersionUID = 1L;

			@Override
			protected AbstractActor getActor() {
				return identifiable;
			}
			
		}
		
	}
	
	@Getter @Setter
	public static class Form<ACTOR extends AbstractActor> extends AbstractActorEditFormModel.AbstractDefault.Default<ACTOR> implements Serializable {

		private static final long serialVersionUID = 2646571878912106597L;
		
	}
	
}
