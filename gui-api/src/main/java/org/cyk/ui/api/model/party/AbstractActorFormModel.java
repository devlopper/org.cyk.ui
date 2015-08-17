package org.cyk.ui.api.model.party;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs.Layout;

@Getter @Setter
public abstract class AbstractActorFormModel<ACTOR extends AbstractActor> extends AbstractFormModel<ACTOR>  implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;

	@IncludeInputs(layout=Layout.VERTICAL) 
	private PersonFormModel personFormModel;

	public AbstractActorFormModel() {
		super();
		this.personFormModel = new PersonFormModel();
	}
	
	@Override
	public void setIdentifiable(ACTOR actor) {
		if(actor.getPerson()==null)
			actor.setPerson(new Person());
		personFormModel.setIdentifiable(actor.getPerson());
		super.setIdentifiable(actor);
	}
	
}
