package org.cyk.ui.api.model.party;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.party.person.AbstractActor;

@Getter @Setter
public class DefaultActorReadFormModel extends AbstractActorReadFormModel<AbstractActor>  implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;

	public DefaultActorReadFormModel(AbstractActor actor) {
		super(actor);
	}
			
}
