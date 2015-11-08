package org.cyk.ui.web.primefaces.page.tools;

import java.io.Serializable;

import org.cyk.system.root.model.party.person.AbstractActor;

public class DefaultActorCrudOnePageAdapter<ACTOR extends AbstractActor> extends AbstractActorCrudOnePageAdapter<ACTOR> implements Serializable {

	private static final long serialVersionUID = 4370361826462886031L;

	public DefaultActorCrudOnePageAdapter(Class<ACTOR> entityTypeClass) {
		super(entityTypeClass);
	}

}
