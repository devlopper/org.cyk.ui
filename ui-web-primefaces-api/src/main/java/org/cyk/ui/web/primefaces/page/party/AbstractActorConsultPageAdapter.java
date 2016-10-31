package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage;

//TODO why you do not put it on listened class itself
public abstract class AbstractActorConsultPageAdapter<ACTOR extends AbstractActor> extends AbstractConsultPage.ConsultPageListener.Adapter.Default<ACTOR> implements Serializable {

	private static final long serialVersionUID = 4370361826462886031L;

	public AbstractActorConsultPageAdapter(Class<ACTOR> entityTypeClass) {
		super(entityTypeClass);	
	}
	
	/**/
	
	public static class Default<ACTOR extends AbstractActor> extends AbstractActorConsultPageAdapter<ACTOR> implements Serializable {

		private static final long serialVersionUID = 4370361826462886031L;

		public Default(Class<ACTOR> entityTypeClass) {
			super(entityTypeClass);
		}

	}

}
