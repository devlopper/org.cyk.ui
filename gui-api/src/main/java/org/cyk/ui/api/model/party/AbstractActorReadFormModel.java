package org.cyk.ui.api.model.party;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs.Layout;

@Getter @Setter
public abstract class AbstractActorReadFormModel<ACTOR extends AbstractActor> extends AbstractActorFormModel<ACTOR>  implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;

	@IncludeInputs(layout=Layout.VERTICAL) 
	private PersonReadFormModel personFormModel;

	public AbstractActorReadFormModel() {
		super();
		this.personFormModel = new PersonReadFormModel();
	}
	
	@Override
	public void setIdentifiable(ACTOR actor) {
		personFormModel.setIdentifiable(actor.getPerson());
		super.setIdentifiable(actor);
	}
	
}
