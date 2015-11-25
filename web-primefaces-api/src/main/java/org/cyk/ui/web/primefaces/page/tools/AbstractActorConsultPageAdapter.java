package org.cyk.ui.web.primefaces.page.tools;

import java.io.Serializable;

import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.ui.web.primefaces.page.ConsultPageListener;

public abstract class AbstractActorConsultPageAdapter<ACTOR extends AbstractActor> extends ConsultPageListener.Adapter.Default<ACTOR> implements Serializable {

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
