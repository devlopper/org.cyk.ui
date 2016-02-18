package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.test.model.Actor;
import org.cyk.ui.web.primefaces.page.AbstractSelectOnePage;
import org.cyk.ui.web.primefaces.test.business.GuiBusinessLayer;

@Getter @Setter
public class ActorSelectOnePageAdapter extends AbstractSelectOnePage.Listener.Adapter.Default<Actor,String> implements Serializable {

	private static final long serialVersionUID = -7392513843271510254L;
	
	public ActorSelectOnePageAdapter() {
		super(Actor.class);
	}
	
	@Override
	public Actor findByIdentifier(String identifier) {
		return GuiBusinessLayer.getInstance().getActorBusiness().findByRegistrationCode(identifier);
	}

}
