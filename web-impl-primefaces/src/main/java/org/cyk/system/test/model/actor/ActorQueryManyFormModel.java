package org.cyk.system.test.model.actor;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.test.model.actor.Actor;
import org.cyk.ui.api.model.AbstractQueryManyFormModel;
import org.cyk.ui.api.model.party.AbstractActorQueryManyFormModel;
import org.cyk.utility.common.annotation.FieldOverride;
import org.cyk.utility.common.annotation.FieldOverrides;

@Getter @Setter @FieldOverrides(value={@FieldOverride(name=AbstractQueryManyFormModel.FIELD_IDENTIFIABLES,type=Actor.class)})
public class ActorQueryManyFormModel extends AbstractActorQueryManyFormModel.Default<Actor> implements Serializable {
	private static final long serialVersionUID = -3756660150800681378L;
	
}