package org.cyk.ui.api.model.party;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Person;

@Getter @Setter
public abstract class AbstractActorEditFormModel<ACTOR extends AbstractIdentifiable> extends AbstractPersonEditFormModel<ACTOR>  implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;

	@Override
	protected Person getPerson() {
		return getActor().getPerson();
	}
	
	protected abstract AbstractActor getActor();
	
	@Override
	public void setIdentifiable(ACTOR identifiable) {
		if(identifiable instanceof AbstractActor && ((AbstractActor)identifiable).getPerson()==null)
			((AbstractActor)identifiable).setPerson(new Person());
		super.setIdentifiable(identifiable);
	}
	
	@Override
	public void write() {
		super.write();
		//if(Crud.CREATE.equals(crud))
		//	setBusinessValues(getPerson());
	}
	
	/**/
	
	@Getter @Setter
	public static abstract class AbstractDefault<ACTOR extends AbstractActor> extends AbstractActorEditFormModel<ACTOR>  implements Serializable {

		private static final long serialVersionUID = -3897201743383535836L;
	
		@Override
		protected AbstractActor getActor() {
			return identifiable;
		}
		
		/**/
		
		@Getter @Setter
		public static class Default<ACTOR extends AbstractActor> extends AbstractDefault<ACTOR>  implements Serializable {

			private static final long serialVersionUID = -3897201743383535836L;
				
		}
	}
	
}
