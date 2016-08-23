package org.cyk.system.test.model.actor;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.test.business.GuiBusinessLayer;
import org.cyk.system.test.model.actor.Actor;
import org.cyk.ui.web.primefaces.page.AbstractSelectOnePage;

@Getter @Setter
public class ActorSelectOnePageAdapter extends AbstractSelectOnePage.Listener.Adapter.Default<Actor,String> implements Serializable {

	private static final long serialVersionUID = -7392513843271510254L;
	
	public ActorSelectOnePageAdapter() {
		super(Actor.class);
	}
	
	@Override
	public Actor findByIdentifier(String identifier) {
		return GuiBusinessLayer.getInstance().getActorBusiness().findByGlobalIdentifierCode(identifier);
	}

}
