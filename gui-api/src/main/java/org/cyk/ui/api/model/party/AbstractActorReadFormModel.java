package org.cyk.ui.api.model.party;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.annotation.user.interfaces.Sequence;
import org.cyk.utility.common.annotation.user.interfaces.Sequence.Direction;

@Getter @Setter
public abstract class AbstractActorReadFormModel<ACTOR extends AbstractActor> extends AbstractPersonReadFormModel<ACTOR>  implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;

	public static final String FIELD_REGISTRATION_CODE = "registrationCode";
	
	@Input @InputText @Sequence(direction=Direction.BEFORE,field="firstName") private String registrationCode;

	public AbstractActorReadFormModel(ACTOR actor) {
		super(actor);
	}
	
	@Override
	protected Person getPerson(ACTOR actor) {
		return actor.getPerson();
	}
	
}
