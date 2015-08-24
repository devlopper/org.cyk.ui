package org.cyk.ui.api.model.party;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;

@Getter @Setter
public abstract class AbstractActorFormModel<ACTOR extends AbstractActor> extends AbstractFormModel<ACTOR>  implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;

	
}
