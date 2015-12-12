package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;

import org.cyk.ui.test.model.Actor;
import org.cyk.ui.web.primefaces.page.SelectPageListener;
import org.cyk.ui.web.primefaces.test.business.GuiBusinessLayer;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ActorSelectPageAdapter extends SelectPageListener.Adapter.Default<Actor,String> implements Serializable {

	private static final long serialVersionUID = -7392513843271510254L;
	
	public ActorSelectPageAdapter() {
		super(Actor.class);
		//type = SelectPageListener.Type.IDENTIFIER;
	}
	
	@Override
	public Actor findByIdentifier(String identifier) {
		return GuiBusinessLayer.getInstance().getActorBusiness().findByRegistrationCode(identifier);
	}

}
