package org.cyk.ui.api.model.party;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.annotation.user.interfaces.Sequence;
import org.cyk.utility.common.annotation.user.interfaces.Sequence.Direction;

@Getter @Setter @NoArgsConstructor
public class ActorConsultFormModel extends AbstractPersonReadFormModel<AbstractActor>  implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;

	@Input @InputText @Sequence(direction=Direction.BEFORE,field="firstName") private String registrationCode;
	
	@Override
	protected Person getPerson() {
		return identifiable.getPerson();
	}
		
	@Override
	public void read() {
		registrationCode = identifiable.getRegistration().getCode();
		super.read();
	}
			
}
